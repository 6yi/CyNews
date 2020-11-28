package com.cy.news.newsprovider;

import com.cy.news.api.service.NewsService;
import com.cy.news.newsprovider.dao.NewsDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class NewsProviderApplicationTests {

    @Autowired
    private NewsDao newsDao;

    @Autowired
    NewsService newsService;

    @Test
    void contextLoads() throws JsonProcessingException {
//        System.out.println(newsDao.selectHotNews(new Date(), null));
//        System.out.println(new ObjectMapper().writeValueAsString(newsDao.selectContentById(100000).get(0)));

    }


//    @Test
//    void contextLoads2() {
//        newsService.getIndexNews();
//    }

}
