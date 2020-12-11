package com.cy.news.newsprovider.service;

import cn.hutool.core.date.DateUtil;
import com.cy.news.api.service.NewsService;
import com.cy.news.common.DTO.ResultDTO;
import com.cy.news.common.Pojo.NewsMessage;
import com.cy.news.common.Pojo.NewsWithBLOBs;
import com.cy.news.common.VO.ContentAndMsgVo;
import com.cy.news.newsprovider.Utils.RedisLockUtil;
import com.cy.news.newsprovider.dao.NewsDao;
import com.cy.news.newsprovider.dao.NewsMessageDao;
import com.youbenzi.mdtool.tool.MDTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;



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
    private RedisTemplate<Object, Object> redisTemplate;

    //用来操作点赞和浏览数量
    @Autowired
    private RedisTemplate<String, String> numberRedisTemplate;

    @Autowired
    private RedisLockUtil redisLockUtil;



    /**
     * @author 6yi
     * @date 2020/11/27
     * @return
     * @Description 首页获取热点新闻
     *
     *
     * @param type 新闻类型
     * @param start 起始位置
     * @param end 结束位置
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
                redisLockUtil.lock("hotNews:lock",String.valueOf(Thread.currentThread().getId()));
                hotNews=redisTemplate.opsForZSet().reverseRange(key, start, end);
                if(hotNews==null||hotNews.isEmpty()){
                    if ("index".equals(type)){
                        hotNews = ResetAndGetHotNewsSet(key,start,end,null);
                    }else{
                        hotNews = ResetAndGetHotNewsSet(key,start,end,type);
                    }
                }
            }finally {
                redisLockUtil.unLock("hotNews:lock",String.valueOf(Thread.currentThread().getId()));
            }
        }
        return ResultDTO.builder().code(200).data(hotNews).build();

    }


    /**
     * @author 6yi
     * @date 2020/12/10
     * @return
     * @Description 正文获取
     *
     * @param nId 新闻id
     **/
    @Override
    public ResultDTO getNewsContent(Long nId) {
        String key="news:content:"+nId;
        NewsWithBLOBs news = (NewsWithBLOBs) redisTemplate.opsForValue().get(key);
        ContentAndMsgVo data=null;
        if(news==null){
            try {
                redisLockUtil.lock("content:lock",String.valueOf(Thread.currentThread().getId()));
                if((news = (NewsWithBLOBs)redisTemplate.opsForValue().get(key))==null){
                    NewsWithBLOBs newsWithBLOBs = newsDao.selectContentById(nId);
                    if(newsWithBLOBs==null){
                        redisTemplate.opsForValue().set(key,"",1,TimeUnit.SECONDS);
                        return ResultDTO.builder().code(200).data(null).build();
                    }else{
                        data=getContentAndMsgVo(nId,newsWithBLOBs);
                        redisTemplate.opsForValue().set(key,news,2,TimeUnit.HOURS);
                    }
                }else{
                    data=getContentAndMsgVo(nId,news);
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            } finally {
                redisLockUtil.unLock("content:lock",String.valueOf(Thread.currentThread().getId()));
            }
        }else{
            data = getContentAndMsgVo(nId, news);
        }
        //增加浏览量
        incrWatch(nId);

        return ResultDTO.builder().code(200).data(data).build();
    }

    /**
     * @author 6yi
     * @date 2020/12/10
     * @return
     * @Description 判断是否已经点过赞
     *
     * @param nId 新闻id
     * @param uId 用户id
     **/
    @Override
    public ResultDTO isLikeNews(Long nId, Integer uId) {
        if(numberRedisTemplate.opsForHash().hasKey("userLike:" + uId, nId.toString())){
            return ResultDTO.builder().code(300).data("已点赞").build();
        }
        return ResultDTO.builder().code(200).data("未点赞").build();
    }


    /**
     * @author 6yi
     * @date 2020/12/10
     * @return
     * @Description  封装成Vo
     **/
    private ContentAndMsgVo getContentAndMsgVo(Long nId, NewsWithBLOBs news) {
        ContentAndMsgVo data;
        NewsMessage message;

        try{
            message= NewsMessage.builder()
                    .mLike(Integer.parseInt((String) Objects.requireNonNull(numberRedisTemplate.opsForHash().get("newsMessage:" + nId, "like"))))
                    .mWatch(Integer.parseInt((String) Objects.requireNonNull(numberRedisTemplate.opsForHash().get("newsMessage:" + nId, "watch")))).build();
        }catch (Exception e){
            message=NewsMessage.builder().mWatch(0).mLike(0).build();
        }

        news.setNContent(MDTool.markdown2Html(news.getNContent()));

        data = ContentAndMsgVo.builder().news(news).newsMessage(message).build();
        return data;

    }




    /**
     * @author 6yi
     * @date 2020/12/10
     * @return
     * @Description  增加浏览数量
     **/
    public void incrWatch(Long nId) {
        if((numberRedisTemplate.opsForHash().get("newsMessage:"+nId, "watch"))==null){
            try{
                redisLockUtil.lock("watch:lock",String.valueOf(Thread.currentThread().getId()));
                if((numberRedisTemplate.opsForHash().get("newsMessage:"+nId, "watch"))==null){
                    numberRedisTemplate.opsForHash().put("newsMessage:"+nId, "watch","1");
                }else{
                    numberRedisTemplate.opsForHash().increment("newsMessage:"+nId, "watch",1);
                }
            }finally {
                redisLockUtil.unLock("watch:lock",String.valueOf(Thread.currentThread().getId()));
            }
        }else {
            numberRedisTemplate.opsForHash().increment("newsMessage:"+nId,"watch",1);
        }
    }




    /**
     * @author 6yi
     * @date 2020/12/10
     * @return
     * @Description   从数据库获取新闻并且加入到redis缓存中
     **/
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
                        if (hotNews.getNImg() != null) {
                            hotNews.setImgSrc(hotNews.getNImg().split("\\|"));
                            hotNews.setNImg(null);
                        }

                        NewsMessage msg = NewsMessage.builder().mLike(like).mWatch(watch).build();
                        hotNews.setNewsMessage(msg);

                        //热度值算法 瞎吉儿写的，先凑合用着
                        long hotKey  =  (hotNews.getNDate().getTime() - nowTime) / 5000000 +
                                        hotNews.getNewsMessage().getMLike() * 10 +
                                        hotNews.getNewsMessage().getMWatch() * 6 +
                                        hotNews.getNStatus() * 10;

                        hotNews.setHotKey(hotKey);
                        return hotNews;
                    }).forEach(h->{

                        redisTemplate.opsForZSet().add(key,h,h.getHotKey());

            });

            redisTemplate.expire(key,1, TimeUnit.HOURS);
            return redisTemplate.opsForZSet().reverseRange(key, start, end);

    }


}
