package com.cy.news.emailserver.service;

import cn.hutool.core.net.URLDecoder;
import cn.hutool.core.util.RandomUtil;
import com.cy.news.api.service.EmailService;
import com.cy.news.emailserver.utils.EmailUtils;
import com.cy.news.pojo.DTO.ResultDTO;
import com.cy.news.pojo.Exception.EmailRetErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class EmailServiceImpl implements EmailService {

     @Value("${spring.mail.username}")
    private   String sender="yj979399417@163.com";

    @Value("${spring.mail.title}")
    private String title="点击链接验证邮箱完成注册";

    @Value("${spring.mail.addr")
    private String addr;



    @Autowired
    EmailUtils emailUtils;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public ResultDTO SendEmail(Integer id, String email) {
     //   String title="点击链接验证邮箱完成注册";

      //  String sender="yj979399417@163.com";
        String rand= RandomUtil.randomString(8);

        String text="请点击 "+addr+"?id="+id+"&"+"code="+rand;

        redisTemplate.opsForValue().set(id.toString(),rand,5, TimeUnit.MINUTES);

        String timeOutValue = redisTemplate.opsForValue().get(id.toString());

        emailUtils.send(sender,email,title,text);

        return  ResultDTO.builder().code(EmailRetErrorCode.OK).build();

    }


}
