package com.cy.news.newsprovider;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDubbo(scanBasePackages = "com.cy.news.newsprovider.service")
public class NewsProviderApplication {

    @Value("${server.port}")
    private Integer workId;

    public static void main(String[] args) {
        SpringApplication.run(NewsProviderApplication.class, args);
    }




    @Bean
    public Snowflake snowflake(){
        return IdUtil.createSnowflake(workId,1);
    }

}









