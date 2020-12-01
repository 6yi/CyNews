package com.cy.news.newsprovider.service;

import cn.hutool.core.date.DateUtil;
import com.cy.news.api.service.NewsService;
import com.cy.news.api.service.UserService;
import com.cy.news.common.DTO.ResultDTO;
import com.cy.news.common.Pojo.Comments;
import com.cy.news.common.Pojo.NewsMessage;
import com.cy.news.common.Pojo.NewsWithBLOBs;
import com.cy.news.newsprovider.dao.CommentsDao;
import com.cy.news.newsprovider.dao.NewsDao;
import com.cy.news.newsprovider.dao.NewsMessageDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @ClassName NewsServiceImpl
 * @Author 6yi
 * @Date 2020/11/26 21:48
 * @Version 1.0
 * @Description:
 */

@DubboService(version = "1.0.0")
@Service
@Slf4j
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsDao newsDao;

    @Autowired
    private NewsMessageDao newsMessageDao;

    @Autowired
    private CommentsDao commentsDao;

    @DubboReference
    private UserService userService;


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final Lock hotNewsReentrantLock=new ReentrantLock();
    private static final Lock commentsReentrantLock=new ReentrantLock();
    private static final Lock contentReentrantLock=new ReentrantLock();

    /**
     * @author 6yi
     * @date 2020/11/27
     * @return
     * @Description 首页获取热点新闻
     **/
    @Override
    public ResultDTO getNews(String type,Long start,Long end) {

        String key="hotNews:"+type;
        Set<String> hotNews = redisTemplate.opsForZSet().reverseRange(key, start, end);
        if(hotNews==null||hotNews.isEmpty()){
            log.info("读数据库");
            try{
                //缓存失效并且大量用户访问时,先加锁
                // 只有一个用户能读取数据库,然后再释放锁,其它用户拿到锁后先判断现在是否能获取到数据
                hotNewsReentrantLock.lock();
                hotNews=redisTemplate.opsForZSet().reverseRange(key, start, end);
                if(hotNews==null||hotNews.isEmpty()){
                    if ("index".equals(type)){
                        hotNews = ResetAndGetHotNewsSet(key,start,end,null);
                    }else{
                        hotNews = ResetAndGetHotNewsSet(key,start,end,type);
                    }
                }
            }finally {
                hotNewsReentrantLock.unlock();
            }
        }
        return ResultDTO.builder().code(200).data(hotNews).build();

    }



    /**
     * @author 6yi
     * @date 2020/11/28
     * @return
     * @Description 分页查询评论
     **/
    @Override
    public ResultDTO getNewsComments(Long nId, Integer page,Integer number) {

        String key = "comments:"+nId+":"+page;
        String commentsJson = redisTemplate.opsForValue().get(key);
        if(commentsJson==null){
            log.info("走数据库");
            try{
                commentsReentrantLock.lock();
                if((commentsJson=redisTemplate.opsForValue().get(key))==null){
                    PageHelper.offsetPage(page,number);
                    List<Comments> comments = commentsDao.selectCommentsByNid(nId);
                    commentsJson = new ObjectMapper().writeValueAsString(comments);
                    redisTemplate.opsForValue().set(key,commentsJson,2,TimeUnit.HOURS);
                }
            }catch (Exception e) {
                throw new RuntimeException("json错误");
            }finally{
                commentsReentrantLock.unlock();
            }
        }
        return ResultDTO.builder().code(200).data(commentsJson).build();
    }

    //todo 正文获取
    @Override
    public ResultDTO getNewsContent(Long nId) {
        String key="news:content:"+nId;
        String content = redisTemplate.opsForValue().get(key);
        if(content==null){
            try {
                contentReentrantLock.lock();
                if((content = redisTemplate.opsForValue().get(key))==null){
                    NewsWithBLOBs newsWithBLOBs = newsDao.selectContentById(nId);
                    content = new ObjectMapper().writeValueAsString(newsWithBLOBs);
                    redisTemplate.opsForValue().set(key,content,2,TimeUnit.HOURS);
                }
            } catch (Exception e) {
                throw new RuntimeException("jsonException");
            } finally {
                contentReentrantLock.unlock();
            }
        }
        return ResultDTO.builder().code(200).data(content).build();
    }


    //todo 点赞
    @Override
    public ResultDTO incrLike(Long nId,Integer uId) {
        NewsMessage newsMessage = (NewsMessage)redisTemplate.opsForHash().get("newsMessage", nId);
        if(newsMessage==null){
            newsMessage=newsMessageDao.findOneByNId(nId);
        }
        Integer mLike=0;
        try {
            mLike=newsMessage.getMLike();
            newsMessage.setMLike(++mLike);
        }finally {

        }
        redisTemplate.opsForHash().put("newsMessage",nId,newsMessage);
        userService.addUserLikeNews(nId,uId);

        return ResultDTO.builder().code(mLike).build();
    }

    //todo 取消点赞
    @Override
    public ResultDTO decLike(Long id,Integer uId) {
        return null;
    }


    private Set<String> ResetAndGetHotNewsSet(String key,long start,long end,String type) {
            redisTemplate.delete(key);
            long nowTime = System.currentTimeMillis();
            Date date = new Date();

            newsDao.selectHotNews(date, DateUtil.offsetMonth(date,-1),type)
                    .stream()
                    .map(hotNews -> {
                        NewsMessage newsMessage = newsMessageDao.findOneByNId(hotNews.getNId());
                        hotNews.setNewsMessage(newsMessage);
                        log.info(hotNews.toString());
                        if (hotNews.getNImg() != null) {
                            hotNews.setImgSrc(hotNews.getNImg().split("\\|"));
                            hotNews.setNImg(null);
                        }
                        //热度值算法
                        long hotKey  =  (hotNews.getNDate().getTime() - nowTime) / 5000000 +
                                        hotNews.getNewsMessage().getMLike() * 10 +
                                        hotNews.getNewsMessage().getMWatch() * 6 +
                                        hotNews.getNStatus() * 10;
                        hotNews.setHotKey(hotKey);
                        return hotNews;
                    }).forEach(h->{
                        try {

                            redisTemplate.opsForZSet().add(key,new ObjectMapper().writeValueAsString(h),h.getHotKey());

                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
            });

            redisTemplate.expire(key,3, TimeUnit.HOURS);
            return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }


}
