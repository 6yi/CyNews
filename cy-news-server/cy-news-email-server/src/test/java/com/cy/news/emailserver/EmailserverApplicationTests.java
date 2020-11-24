package com.cy.news.emailserver;


import com.cy.news.emailserver.service.EmailServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class EmailserverApplicationTests {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    EmailServiceImpl emailService;


    @Test
    void contextLoads() {

    }

}
