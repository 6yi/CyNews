package com.cy.news.newsprovider;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.cy.news.common.Exception.GlobalExceptionHandler;
import com.cy.news.common.Exception.aop.GlobalExceptionHandlerAop;
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


    /**
     * @author 6yi
     * @date 2020/12/11
     * @return
     * @Description hutool的雪花ID生成器，注入workID和数据中心ID
     **/
    @Bean
    public Snowflake snowflake(){
        return IdUtil.createSnowflake(SnowFlakeUtils.getWorkId(),SnowFlakeUtils.getDataCenterId());
    }


    @Bean
    public GlobalExceptionHandler globalExceptionHandler(){
        return new GlobalExceptionHandler();
    }

    @Bean()
    public GlobalExceptionHandlerAop exceptionHandlerAop(){
        return new GlobalExceptionHandlerAop();
    }


}









