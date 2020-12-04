package com.cy.news.emailserver.MQ;


import com.cy.news.common.MQ.ErrorMsgMQEntity;
import com.cy.news.emailserver.dao.AdminDao;
import com.cy.news.emailserver.service.EmailServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



@Slf4j
@Component
@RocketMQMessageListener(topic = "${mq.global.error.topic}",consumerGroup = "${rocketmq.global.exception.group}")
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
            String s = new String(messageExt.getBody(), "UTF-8");
            ErrorMsgMQEntity errorMsgMQEntity = new ObjectMapper().readValue(s, ErrorMsgMQEntity.class);
            String content = errorLogContent.replace("{CONTENT}", errorMsgMQEntity.getErrorMsg())
                    .replace("{TIME}",errorMsgMQEntity.getTime());
            String title="错误日志: "+errorMsgMQEntity.getTime();
            adminDao.selectAllDev().stream().forEach(admin->{
                new Thread(()->{
                    emailService.sendErrorLog(admin.getEMail(),title,content);
                }).start();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
