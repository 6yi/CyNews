package com.cy.news.admin;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.cy.news.admin.config.CorsFilter;
import com.cy.news.admin.config.jwtFilter;
import com.cy.news.common.Utils.SnowFlakeUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }


    @Bean
    public Snowflake snowflake(){
        return IdUtil.createSnowflake(SnowFlakeUtils.getWorkId(),SnowFlakeUtils.getDataCenterId());
    }

    @Bean
    public FilterRegistrationBean corsFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CorsFilter());
        registration.addUrlPatterns("/*");
        registration.setName("corsFilter");
        //将其注册在JWT过滤器的前面
        registration.setOrder(0);
        return registration;

    }

    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new jwtFilter());
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/news/*");
        registrationBean.setOrder(1);
        registrationBean.addUrlPatterns(urlPatterns.toArray(new String[urlPatterns.size()]));
        return registrationBean;
    }
}
