package com.cy.news.newsprovider.controller;

import com.cy.news.common.DTO.ResultDTO;
import com.cy.news.newsprovider.service.NewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName Test
 * @Author 6yi
 * @Date 2020/11/27 12:21
 * @Version 1.0
 * @Description:
 */

@RestController
public class Test {

    @Autowired
    NewsServiceImpl newsService;

//    @GetMapping("/test")
//    public ResultDTO test(){
//        return newsService.getNews(0L,15L);
//    }

}
