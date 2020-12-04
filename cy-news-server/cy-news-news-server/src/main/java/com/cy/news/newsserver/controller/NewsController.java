package com.cy.news.newsserver.controller;

import com.cy.news.api.service.NewsMessageService;
import com.cy.news.api.service.NewsService;
import com.cy.news.common.DTO.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName NewsController
 * @Author 6yi
 * @Date 2020/11/28 13:14
 * @Version 1.0
 * @Description:
 */

@RestController
@Slf4j
public class NewsController {

    @DubboReference(version = "1.0.0")
    NewsService newsService;

    @DubboReference(version = "1.0.0")
    NewsMessageService newsMessageService;
    
    @GetMapping("/news/{type}/{start}/{end}")
    public ResultDTO getNews(@PathVariable("type")String type,
                             @PathVariable(name = "start")Long start,
                             @PathVariable(name = "end")Long end){
        return newsService.getNews(type,start,end);
    }

    @PutMapping("/like/{nId}/")
    public ResultDTO incrLike(@PathVariable("nId")Long nId,
                              HttpServletRequest request){
        String uId = request.getHeader("uId");
        log.info("点赞："+uId);
        return newsMessageService.incrLike(nId,Integer.parseInt(uId));
    }

    @DeleteMapping("/declike/{nId}/{uId}")
    public ResultDTO decLike(@PathVariable("nId")Long nId,
                             HttpServletRequest request){
        String uId = request.getHeader("authority");
        return newsMessageService.decLike(nId,Integer.parseInt(uId));
    }


    @GetMapping("/content/{nId}")
    public ResultDTO getContent(@PathVariable("nId")Long nId){
        return newsService.getNewsContent(nId);
    }






}
