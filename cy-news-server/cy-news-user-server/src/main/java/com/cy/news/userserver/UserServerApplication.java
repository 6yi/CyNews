package com.cy.news.userserver;

import com.cy.news.common.Exception.GlobalExceptionHandler;
import com.cy.news.common.Exception.aop.GlobalExceptionHandlerAop;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableDiscoveryClient
public class UserServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServerApplication.class, args);
    }

    //全局异常处理器
    @Bean
    public GlobalExceptionHandler exceptionHandler(){
        return new GlobalExceptionHandler();
    }

    //对异常结果进行邮件发送
    @Bean
    public GlobalExceptionHandlerAop exceptionHandlerAop(){
        return new GlobalExceptionHandlerAop();
    }

}
