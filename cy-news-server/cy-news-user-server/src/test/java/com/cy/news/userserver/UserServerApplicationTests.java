package com.cy.news.userserver;


import com.cy.news.userserver.utils.TencentCosUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;

@SpringBootTest
class UserServerApplicationTests {
    @Autowired
    TencentCosUtils tencentCosUtils;
    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Test
    void contextLoads2() {

        System.out.println(applicationContext.getEnvironment().getProperty("oss.regionName"));
    }


    @Test
    void contextLoads() {
        File file = new File("C:\\Users\\6yi\\Pictures\\Saved Pictures\\49f5e9cdly1g2slfi12onj215o0nrwnr.jpg");
        System.out.println(tencentCosUtils.upLoad(file));
    }

}
