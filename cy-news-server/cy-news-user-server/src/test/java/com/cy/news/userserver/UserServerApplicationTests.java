package com.cy.news.userserver;

import cn.hutool.core.lang.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServerApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(Validator.isChinese("我48548"));
    }

}
