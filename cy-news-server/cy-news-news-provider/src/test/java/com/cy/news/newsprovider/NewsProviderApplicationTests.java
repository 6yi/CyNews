package com.cy.news.newsprovider;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import com.cy.news.api.service.NewsService;
import com.cy.news.common.Pojo.News;
import com.cy.news.newsprovider.dao.NewsDao;
import com.github.pagehelper.PageHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@SpringBootTest
class NewsProviderApplicationTests {

    @Autowired
    private RedisTemplate<Object,Object> jsonRedisTemplate;

    @Autowired
    private NewsDao newsDao;
//
//    @Autowired
//    NewsService newsService;


//    @Autowired
//    Snowflake snowflake;

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Test
    void contextLoads() throws IOException {


        redisTemplate.opsForHash().put("z","y","1");

        redisTemplate.opsForHash().increment("z","y",1);

//        System.out.println(Integer.parseInt((String) redisTemplate.opsForHash().get("v", "c")));
    }

    @Test
    void contextLoads2() throws IOException {
        long nowTime = System.currentTimeMillis();
        Date date = new Date();
//        PageHelper.offsetPage(0,2);
//        newsDao.selectHotNews(date, DateUtil.offsetMonth(date, -1), null).forEach(h->{
//            jsonRedisTemplate.opsForList().leftPush("test123",h);
//        });
        Integer page=0;
        long start=page*10;
        long end=start+10;
        List<Object> comments = jsonRedisTemplate.opsForList().range("wu", start, end);
        comments.forEach(h->{
            System.out.println(h);
        });


    }

//    @Test
//    void contextLoads2() {
//        newsService.getIndexNews();
//    }

}
