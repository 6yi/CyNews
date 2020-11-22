package com.cy.news.emailserver.MQ;

import com.cy.news.api.service.EmailService;
import com.cy.news.emailserver.service.EmailServiceImpl;
import com.cy.news.pojo.MQ.EmailMQEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @ClassName SendEmailMQMessageListener
 * @Author 6yi
 * @Date 2020/11/22 14:37
 * @Version 1.0
 * @Description:
 *
 * 异步接收邮件发送消息
 *
 */

@Slf4j
@Component
@RocketMQMessageListener(topic = "${mq.user.topic}",consumerGroup = "${rocketmq.producer.group}")
public class SendEmailMQMessageListener implements RocketMQListener<MessageExt> {

    @Autowired
    EmailServiceImpl emailService;



    @Override
    public void onMessage(MessageExt message) {
        try {
            //解析消息内容
            String jsonString = new String(message.getBody(), "UTF-8");
            EmailMQEntity mqEntity = new ObjectMapper().readValue(jsonString, EmailMQEntity.class);
            emailService.sendEmail(mqEntity.getUId(),mqEntity.getEmail());
            log.info("邮件发送成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
