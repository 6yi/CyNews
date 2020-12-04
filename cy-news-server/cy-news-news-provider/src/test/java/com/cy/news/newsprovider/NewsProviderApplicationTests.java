package com.cy.news.newsprovider;

import cn.hutool.core.lang.Snowflake;
import com.cy.news.api.service.NewsService;
import com.cy.news.common.Pojo.News;
import com.cy.news.newsprovider.dao.NewsDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import java.io.IOException;

@SpringBootTest
class NewsProviderApplicationTests {

//    @Autowired
//    private NewsDao newsDao;
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


    }

//    @Test
//    void contextLoads2() {
//        newsService.getIndexNews();
//    }

}
