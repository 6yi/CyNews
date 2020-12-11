package com.cy.news.newsserver.controller;

import com.cy.news.api.service.NewsMessageService;
import com.cy.news.common.DTO.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName NewsMessageController
 * @Author 6yi
 * @Date 2020/12/5 19:28
 * @Version 1.0
 * @Description:
 */
@Slf4j
@RestController
public class NewsMessageController {

    @DubboReference(version = "1.0.0")
    NewsMessageService newsMessageService;


    @PutMapping("/like/{nId}/")
    public ResultDTO incrLike(@PathVariable("nId")Long nId,
                              HttpServletRequest request){
        String uId = request.getHeader("uId");
        log.info("点赞："+uId);
        return newsMessageService.incrLike(nId,Integer.parseInt(uId));
    }

    @DeleteMapping("/declike/{nId}")
    public ResultDTO decLike(@PathVariable("nId")Long nId,
                             HttpServletRequest request){
        String uId = request.getHeader("uId");
        return newsMessageService.decLike(nId,Integer.parseInt(uId));
    }


}
