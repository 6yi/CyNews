package com.cy.news.emailserver.MQ;

import com.cy.news.api.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(topic = "${mq.global.topic}",consumerGroup = "${rocketmq.exception.group}")
public class GlobalExceptionMessageLister implements RocketMQListener<MessageExt> {
    @Autowired
    EmailService emailService;

    @Override
    public void onMessage(MessageExt messageExt) {

    }
}
