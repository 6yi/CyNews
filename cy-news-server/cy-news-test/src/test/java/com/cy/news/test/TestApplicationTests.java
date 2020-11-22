package com.cy.news.test;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.nacos.common.utils.MD5Utils;
import com.cy.news.api.service.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class TestApplicationTests {

    @DubboReference(version = "1.0.0")
    private UserService userService;


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        String pwd = "admin";
        String s = SecureUtil.md5(pwd);
        System.out.println(s);
//        redisTemplate.opsForValue().set("mail:lzheng","aasdasd",1L, TimeUnit.SECONDS);

    }

}
