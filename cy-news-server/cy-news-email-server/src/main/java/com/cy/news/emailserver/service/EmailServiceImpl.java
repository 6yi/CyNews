package com.cy.news.emailserver.service;

import cn.hutool.core.net.URLDecoder;
import cn.hutool.core.util.RandomUtil;
import com.cy.news.api.service.EmailService;
import com.cy.news.emailserver.utils.EmailUtils;
import com.cy.news.pojo.DTO.ResultDTO;
import com.cy.news.pojo.Exception.EmailRetErrorCode;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@DubboService(version = "1.0.1")
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private   String sender;//发件人

    @Value("${spring.mail.title}")
    private String title;

    @Value("${spring.mail.addr}")
    private String addr;

    @Autowired
    EmailUtils emailUtils;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public ResultDTO sendEmail(Integer id, String email) {

        String rand= RandomUtil.randomString(8);//随机生成数

        String text="请点击 "+addr+"?id="+id+"&"+"code="+rand;

        redisTemplate.opsForValue().set(id.toString(),rand,5, TimeUnit.MINUTES);//使用redis存储验证码跟id信息


        emailUtils.send(sender,email,title,text);//发送邮件

        return  ResultDTO.builder().code(EmailRetErrorCode.OK).build();

    }


}
