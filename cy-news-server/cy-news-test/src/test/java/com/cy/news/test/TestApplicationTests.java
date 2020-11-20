package com.cy.news.test;

import com.cy.news.api.service.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestApplicationTests {

    @DubboReference(version = "1.0.0")
    private UserService userService;

    @Test
    void contextLoads() {

        userService.login();

    }

}
