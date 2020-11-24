package com.cy.news.emailserver.service;

import cn.hutool.core.util.RandomUtil;
import com.cy.news.api.service.EmailService;
import com.cy.news.emailserver.utils.EmailUtils;
import com.cy.news.common.DTO.ResultDTO;
import com.cy.news.common.Exception.EmailRetErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@DubboService(version = "1.0.0")
public class EmailServiceImpl implements EmailService {

    //发件人
    @Value("${spring.mail.username}")
    private   String sender;

    @Value("${spring.mail.title}")
    private String title;

    @Value("${spring.mail.addr}")
    private String addr;

    @Value("${spring.mail.content}")
    private String content;

    @Autowired
    EmailUtils emailUtils;

    @Autowired
    StringRedisTemplate redisTemplate;

    //同步消息
    @Override
    public ResultDTO sendEmail(Integer id, String email) {
        String rand;
        String isSend = redisTemplate.opsForValue().get("mailServer-sendCode:" + id.toString());
        if(isSend!=null){
            rand=isSend;
        }else{
            //随机生成数
            rand = RandomUtil.randomString(8);
        }
        String text = content.replace("{ADDR}", addr)
                             .replace("{ID}", id.toString())
                             .replace("{CODE}", rand);

        //使用redis存储验证码跟id信息
        log.info("key:   mailServer-sendCode:"+id.toString());
        redisTemplate.opsForValue().set("mailServer-sendCode:"+id.toString(),rand,5L, TimeUnit.MINUTES);
        //发送邮件
        Integer sendStatus = emailUtils.send(sender, email, title, text);
        if (sendStatus.equals(1)){
            return  ResultDTO.builder().code(EmailRetErrorCode.OK).build();
        }else{
            return  ResultDTO.builder().code(EmailRetErrorCode.ERROR).build();
        }

    }


    public ResultDTO sendEmailM(Integer id, String email) {



        return null;
    }


}
