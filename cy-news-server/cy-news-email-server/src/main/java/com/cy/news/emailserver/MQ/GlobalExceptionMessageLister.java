package com.cy.news.emailserver.MQ;

import com.cy.news.api.service.EmailService;
import com.cy.news.common.Pojo.Admin;
import com.cy.news.emailserver.dao.AdminDao;
import com.cy.news.emailserver.service.EmailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@Component
@RocketMQMessageListener(topic = "${mq.global.topic}",consumerGroup = "${rocketmq.exception.group}")
public class GlobalExceptionMessageLister implements RocketMQListener<MessageExt> {
    @Autowired
    EmailServiceImpl emailService;

    @Autowired
    AdminDao adminDao;

    @Value("${spring.mail.errorLogContent}")
    private String errorLogContent;


    @Override
    public void onMessage(MessageExt messageExt) {

        try {
            String content = errorLogContent.replace("{CONTENT}", new String(messageExt.getBody(), "UTF-8"));
            adminDao.selectAllDev().stream().forEach(admin->{

                new Thread(()->{
                    emailService.sendErrorLog(admin.getEMail(),"错误日志",content);
                }).start();

            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }



}
