package com.cy.news.newsserver.controller;

import com.cy.news.api.service.CommentsService;
import com.cy.news.common.DTO.ResultDTO;
import com.cy.news.common.VO.CommentsVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName ContentController
 * @Author 6yi
 * @Date 2020/12/5 19:29
 * @Version 1.0
 * @Description:
 */
@Slf4j
@RestController
public class CommentsController {

    @DubboReference(version = "1.0.0")
    private CommentsService commentsService;

    @GetMapping("/getComments/{nId}/{page}")
    public ResultDTO getComments(@PathVariable("nId")Long nId,
                                 @PathVariable("page")Integer page,
                                 HttpServletRequest request){

       return commentsService.getComments(nId,page,10);
    }

    @PutMapping("/addComments")
    public ResultDTO addComments(@RequestBody CommentsVO commentsVO,HttpServletRequest request){
        String nickName = request.getHeader("nickName");
        String uId = request.getHeader("uId");
        log.info("uId:"+uId);
        log.info(commentsVO.toString());
        return commentsService.addComments(commentsVO.getNId(),
                Integer.parseInt(uId),
                nickName,
                commentsVO.getContent());
    }

}
