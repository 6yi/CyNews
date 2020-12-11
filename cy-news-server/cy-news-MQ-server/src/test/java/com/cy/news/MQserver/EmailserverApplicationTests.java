package com.cy.news.MQserver;


import com.cy.news.MQserver.dao.CommentsDao;
import com.cy.news.MQserver.service.EmailServiceImpl;
import com.cy.news.common.Pojo.Comments;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Date;

@SpringBootTest
class EmailserverApplicationTests {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    EmailServiceImpl emailService;

    @Autowired
    private CommentsDao commentsDao;

    @Test
    void contextLoads() {
        Comments build = Comments.builder().uId(123).cContent("asd").uNickname("asd").nId(1123L).cStatus(1).cId(123L).cDate(new Date()).build();
        commentsDao.insertOneComments(build);

    }

}
