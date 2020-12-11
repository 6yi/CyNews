package com.cy.news.api.service;

import com.cy.news.common.DTO.ResultDTO;
import com.cy.news.common.VO.RegisterUserByEmailVO;
import com.cy.news.common.VO.UserNameLoginVO;

/**
 * @ClassName UserService
 * @Author 6yi
 * @Date 2020/11/19 20:13
 * @Version 1.0
 * @Description:
 */

public interface UserService {



    ResultDTO login(UserNameLoginVO userVO);

    ResultDTO register(RegisterUserByEmailVO registerUserVO);

    ResultDTO updateUserStatus(Integer userStatusCode,Integer userId);

    ResultDTO addUserLikeNews(Long nId,Integer uId);

    ResultDTO delUserLikeNews(Long nId,Integer uId);

    ResultDTO upLoadAvatar(String imgSrc,Integer uId);

}
