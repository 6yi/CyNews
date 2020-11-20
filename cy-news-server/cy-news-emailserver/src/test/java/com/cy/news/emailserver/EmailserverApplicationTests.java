package com.cy.news.emailserver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.Jedis;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class EmailserverApplicationTests {
    @Autowired
    StringRedisTemplate redisTemplate;


    @Test
    void contextLoads() {
        System.out.println(UUID.randomUUID().toString());
    }

}
