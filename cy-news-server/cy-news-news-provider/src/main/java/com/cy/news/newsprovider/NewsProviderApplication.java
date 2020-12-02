package com.cy.news.newsprovider;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.cy.news.common.Utils.SnowFlakeUtils;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDubbo(scanBasePackages = "com.cy.news.newsprovider.service")
public class NewsProviderApplication {



    public static void main(String[] args) {
        SpringApplication.run(NewsProviderApplication.class, args);
    }



    @Bean
    public Snowflake snowflake(){
        return IdUtil.createSnowflake(SnowFlakeUtils.getWorkId(),SnowFlakeUtils.getDataCenterId());
    }

}









