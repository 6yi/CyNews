package com.cy.news.newsserver.controller;

import com.cy.news.api.service.NewsService;
import com.cy.news.common.DTO.ResultDTO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName NewsController
 * @Author 6yi
 * @Date 2020/11/28 13:14
 * @Version 1.0
 * @Description:
 */

@RestController
public class NewsController {

    @DubboReference(version = "1.0.0")
    NewsService newsService;



    @GetMapping("/News/{type}/{start}/{end}")
    public ResultDTO getNews(@PathVariable("type")String type,
                             @PathVariable(name = "start")Long start,
                             @PathVariable(name = "end")Long end){
        return newsService.getNews(type,start,end);
    }





}
