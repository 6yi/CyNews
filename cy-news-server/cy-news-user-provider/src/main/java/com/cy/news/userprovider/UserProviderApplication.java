package com.cy.news.userprovider;

import com.cy.news.common.Exception.GlobalExceptionHandler;
import com.cy.news.common.Exception.aop.GlobalExceptionHandlerAop;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication(scanBasePackages = "com.cy.news")
@MapperScan("com.cy.news.userprovider.dao")
@EnableDubbo(scanBasePackages = "com.cy.news.userprovider.service")
public class UserProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserProviderApplication.class, args);
    }

    //全局异常处理器
    @Bean
    public GlobalExceptionHandler exceptionHandler(){
        return new GlobalExceptionHandler();
    }

    //对异常结果进行邮件发送
    @Bean()
    public GlobalExceptionHandlerAop exceptionHandlerAop(){
        return new GlobalExceptionHandlerAop();
    }
}
