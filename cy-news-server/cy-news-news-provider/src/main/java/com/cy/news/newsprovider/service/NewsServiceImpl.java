package com.cy.news.newsprovider.service;

import cn.hutool.core.date.DateUtil;
import com.cy.news.api.service.NewsService;
import com.cy.news.api.service.UserService;
import com.cy.news.common.DTO.ResultDTO;
import com.cy.news.common.Pojo.Comments;
import com.cy.news.common.Pojo.NewsMessage;
import com.cy.news.common.Pojo.NewsWithBLOBs;
import com.cy.news.common.VO.ContentAndMsgVo;
import com.cy.news.newsprovider.dao.CommentsDao;
import com.cy.news.newsprovider.dao.NewsDao;
import com.cy.news.newsprovider.dao.NewsMessageDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.youbenzi.mdtool.tool.MDTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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



    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    //用来操作点赞和浏览数量
    @Autowired
    private RedisTemplate<String, String> numberRedisTemplate;


    private static final Lock hotNewsReentrantLock=new ReentrantLock();
    private static final Lock commentsReentrantLock=new ReentrantLock();
    private static final Lock contentReentrantLock=new ReentrantLock();
    private static final Lock watchReentrantLock=new ReentrantLock();


    /**
     * @author 6yi
     * @date 2020/11/27
     * @return
     * @Description 首页获取热点新闻
     **/
    @Override
    public ResultDTO getNews(String type,Long start,Long end) {

        String key="hotNews:"+type;
        Set<Object> hotNews = redisTemplate.opsForZSet().reverseRange(key, start, end);
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
        String commentsJson = (String) redisTemplate.opsForValue().get(key);
        if(commentsJson==null){
            log.info("走数据库");
            try{
                commentsReentrantLock.lock();
                if((commentsJson=(String) redisTemplate.opsForValue().get(key))==null){
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
        String content = (String) redisTemplate.opsForValue().get(key);
        ContentAndMsgVo data=null;
        if(content==null){
            try {
                contentReentrantLock.lock();
                if((content = (String)redisTemplate.opsForValue().get(key))==null){
                    NewsWithBLOBs newsWithBLOBs = newsDao.selectContentById(nId);
                    if(newsWithBLOBs==null){
                        redisTemplate.opsForValue().set(key,"",1,TimeUnit.SECONDS);
                        return ResultDTO.builder().code(200).data(null).build();
                    }else{
                        content=newsWithBLOBs.getNContent();
                        data=getContentAndMsgVo(nId,content);
                        redisTemplate.opsForValue().set(key,content,2,TimeUnit.HOURS);
                    }
                }else{
                    data=getContentAndMsgVo(nId,content);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("jsonException");
            } finally {
                contentReentrantLock.unlock();
            }
        }else{
            data = getContentAndMsgVo(nId, content);
        }
        //增加浏览量
        incrWatch(nId);

        return ResultDTO.builder().code(200).data(data).build();
    }

    //封装成Vo
    private ContentAndMsgVo getContentAndMsgVo(Long nId, String content) {
        ContentAndMsgVo data;
        NewsMessage message;
        try{
            message= NewsMessage.builder()
                    .mLike(Integer.parseInt((String) Objects.requireNonNull(numberRedisTemplate.opsForHash().get("newsMessage:" + nId, "like"))))
                    .mWatch(Integer.parseInt((String) Objects.requireNonNull(numberRedisTemplate.opsForHash().get("newsMessage:" + nId, "watch")))).build();
        }catch (Exception e){
            message=NewsMessage.builder().mWatch(0).mLike(0).build();
        }
        content= MDTool.markdown2Html(content);
        log.info(content);
        data = ContentAndMsgVo.builder().content(content).newsMessage(message).build();
        return data;
    }




    //todo 增加浏览数量
    public void incrWatch(Long nId) {
        if((numberRedisTemplate.opsForHash().get("newsMessage:"+nId, "watch"))==null){
            try{
                watchReentrantLock.lock();
                if((numberRedisTemplate.opsForHash().get("newsMessage:"+nId, "watch"))==null){
                    numberRedisTemplate.opsForHash().put("newsMessage:"+nId, "watch","1");
                }else{
                    numberRedisTemplate.opsForHash().increment("newsMessage:"+nId, "watch",1);
                }
            }finally {
                watchReentrantLock.unlock();
            }
        }else {
            numberRedisTemplate.opsForHash().increment("newsMessage:"+nId,"watch",1);
        }
    }





    private Set<Object> ResetAndGetHotNewsSet(String key,long start,long end,String type) {
            redisTemplate.delete(key);
            long nowTime = System.currentTimeMillis();
            Date date = new Date();

            newsDao.selectHotNews(date, DateUtil.offsetMonth(date,-1),type)
                    .stream()
                    .map(hotNews -> {

                        Integer like=0;
                        Integer watch=0;
                        if(numberRedisTemplate.opsForHash().get("newsMessage:" + hotNews.getNId(), "like")==null){
                            numberRedisTemplate.opsForHash().put("newsMessage:" + hotNews.getNId(), "like","0");
                        }else{
                            log.info("newsMessage:" + hotNews.getNId());
                            like=Integer.parseInt((String) Objects.requireNonNull(numberRedisTemplate.opsForHash().get("newsMessage:" + hotNews.getNId(), "like")));
                        }

                        if(numberRedisTemplate.opsForHash().get("newsMessage:" + hotNews.getNId(), "watch")==null){
                            numberRedisTemplate.opsForHash().put("newsMessage:" + hotNews.getNId(), "watch","0");
                        }else{
                            watch=Integer.parseInt((String) Objects.requireNonNull(numberRedisTemplate.opsForHash().get("newsMessage:" + hotNews.getNId(), "watch")));
                        }

                        log.info(hotNews.toString());
                        if (hotNews.getNImg() != null) {
                            hotNews.setImgSrc(hotNews.getNImg().split("\\|"));
                            hotNews.setNImg(null);
                        }

                        NewsMessage msg = NewsMessage.builder().mLike(like).mWatch(watch).build();

                        hotNews.setNewsMessage(msg);

                        //热度值算法
                        long hotKey  =  (hotNews.getNDate().getTime() - nowTime) / 5000000 +
                                        hotNews.getNewsMessage().getMLike() * 10 +
                                        hotNews.getNewsMessage().getMWatch() * 6 +
                                        hotNews.getNStatus() * 10;

                        hotNews.setHotKey(hotKey);
                        return hotNews;
                    }).forEach(h->{

                        redisTemplate.opsForZSet().add(key,h,h.getHotKey());
            });

            redisTemplate.expire(key,3, TimeUnit.HOURS);
            return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }


}
