package com.cy.news.api.service;

import com.cy.news.common.DTO.ResultDTO;

/**
 * @ClassName CommentService
 * @Author 6yi
 * @Date 2020/12/5 1:54
 * @Version 1.0
 * @Description:
 */

public interface CommentsService {

    //TODO 添加评论
    ResultDTO addComments(Long nId,Integer uId,String nickName,String content);

    //TODO 获取评论
    ResultDTO getComments(Long nId,Integer page,Integer number);


}
