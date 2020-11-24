package com.cy.news.userserver;

import com.cy.news.common.Exception.GlobalExceptionHandler;
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

}
