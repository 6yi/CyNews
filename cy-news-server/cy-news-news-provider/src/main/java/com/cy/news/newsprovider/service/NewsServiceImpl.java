package com.cy.news.newsprovider.service;

import cn.hutool.core.date.DateUtil;
import com.cy.news.api.service.NewsService;
import com.cy.news.common.DTO.ResultDTO;
import com.cy.news.newsprovider.dao.NewsDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
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
    NewsDao newsDao;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    private static final ReentrantLock reentrantLock=new ReentrantLock();


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
                //缓存失效并且大量用户访问时,先加锁,只有一个用户能读取数据库,然后再释放锁,其它用户拿到锁后先判断现在是否能获取到数据
                reentrantLock.lock();
                hotNews=redisTemplate.opsForZSet().reverseRange(key, start, end);
                if(hotNews==null||hotNews.isEmpty()){
                    if ("index".equals(type)){
                        hotNews = ResetAndGetHotNewsSet(key,start,end,null);
                    }else{
                        hotNews = ResetAndGetHotNewsSet(key,start,end,type);
                    }
                }
            }finally {
                reentrantLock.unlock();
            }
        }
        return ResultDTO.builder().code(200).data(hotNews).build();

    }


    private Set<String> ResetAndGetHotNewsSet(String key,long start,long end,String type) {
            redisTemplate.delete(key);
            long nowTime=System.currentTimeMillis();
            Date date = new Date();
            newsDao.selectHotNews(date, DateUtil.offsetMonth(date,-1),type)
                    .stream()
                    .map(hotNews -> {
                        log.info(hotNews.toString());
                        if (hotNews.getNImg() != null) {
                            hotNews.setImgSrc(hotNews.getNImg().split("\\|"));
                            hotNews.setNImg(null);
                        }
                        long hotKey = (hotNews.getNDate().getTime() - nowTime) / 5000000 +
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
