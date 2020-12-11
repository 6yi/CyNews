package com.cy.news.newsserver;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class NewsServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsServerApplication.class, args);
    }


}
