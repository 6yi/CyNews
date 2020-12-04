package com.cy.news.admin;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.cy.news.common.Utils.SnowFlakeUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }


    @Bean
    public Snowflake snowflake(){
        return IdUtil.createSnowflake(SnowFlakeUtils.getWorkId(),SnowFlakeUtils.getDataCenterId());
    }

}
