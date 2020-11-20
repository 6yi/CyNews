package com.cy.news.emailserver.service;

import cn.hutool.core.util.RandomUtil;
import com.cy.news.api.service.EmailService;
import com.cy.news.emailserver.utils.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class EmailServiceImpl implements EmailService {
    @Value("${spring.email.username}")
    private   String sender;
    @Value("${spring.email.title}")
    private String title;



    @Autowired
    EmailUtils emailUtils;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Override
    public String SendEmail(Integer id, String email) {



        String rand= RandomUtil.randomString(8);



        String text="请点击 https://192.168.123.81:8088/?userId="+id+"&code="+rand;

        redisTemplate.opsForValue().set(id.toString(),rand,50, TimeUnit.SECONDS);
        emailUtils.send(sender,email,title,text);
        return "ok";

    }


}
