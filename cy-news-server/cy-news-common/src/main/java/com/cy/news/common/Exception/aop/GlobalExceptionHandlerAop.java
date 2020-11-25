package com.cy.news.common.Exception.aop;

import com.cy.news.common.MQ.ErrorMsgMQEntity;
import com.cy.news.common.Utils.ErrorMsgUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName GlobalExceptionHandlerAop
 * @Author 6yi
 * @Date 2020/11/25 11:00
 * @Version 1.0
 * @Description:
 */

@Aspect
@Component
@Slf4j
public class GlobalExceptionHandlerAop {


    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @Pointcut("execution(public * com.cy.news.common.Exception.GlobalExceptionHandler.exceptionHandler(..)))")
    public void exceptionHandler(){};


    @After("exceptionHandler()")
    public void sendEmailToDev(JoinPoint joinPoint) throws Exception {
        Exception errorMsg = (Exception)joinPoint.getArgs()[0];
        String exceptionAllInfo = ErrorMsgUtils.getExceptionAllInfo(errorMsg);
        String time = DateTimeFormatter.ofPattern("yyyy/MM/dd H:m:s").format( ZonedDateTime.now());
        ErrorMsgMQEntity build = ErrorMsgMQEntity.builder().time(time).errorMsg(exceptionAllInfo).build();
        sendExceptionEmail(build);
    }


    private  void sendExceptionEmail(ErrorMsgMQEntity mqEntity) throws Exception {

        Message message = null;
        try {
            message = new Message("errorTopic", "EXCEPTION", "global_error", new ObjectMapper().writeValueAsString(mqEntity).getBytes());
            rocketMQTemplate.getProducer().send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("消息写入成功");
                }

                @Override
                public void onException(Throwable throwable) {
                    //   logger.info("消息写入失败");
                }
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }




}
