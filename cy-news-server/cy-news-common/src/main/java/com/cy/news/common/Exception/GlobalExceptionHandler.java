package com.cy.news.common.Exception;

import com.cy.news.common.DTO.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @ClassName GlobalExceptionHandler
 * @Author 6yi
 * @Date 2020/11/24 16:42
 * @Version 1.0
 * @Description:
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    RocketMQTemplate rocketMQTemplate;
    @ExceptionHandler(value =RuntimeException.class)
    public ResultDTO exceptionHandler(Exception e){


        try {
            sendExceptionEmail(e);
        } catch (Exception mqClientException) {
            mqClientException.printStackTrace();
        }

        return ResultDTO.builder().code(500).data("请检查网络").build();
    }

    private void sendExceptionEmail(Exception e) throws MQClientException, RemotingException, InterruptedException {


        Message message = new Message("errorTopic", "EXCEPTION", "global_error", e.toString().getBytes());

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
    }
}
