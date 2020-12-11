package com.cy.news.MQserver;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo(scanBasePackages = "com.cy.news.MQserver.service")
public class MQserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(MQserverApplication.class, args);
    }

}
