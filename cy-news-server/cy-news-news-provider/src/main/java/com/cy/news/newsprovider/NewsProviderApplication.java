package com.cy.news.newsprovider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo(scanBasePackages = "com.cy.news.newsprovider.service")
public class NewsProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsProviderApplication.class, args);
    }

}









