package com.cy.news.emailserver;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo(scanBasePackages="com.cy.news.emailserver.service")
public class EmailserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailserverApplication.class, args);
    }

}
