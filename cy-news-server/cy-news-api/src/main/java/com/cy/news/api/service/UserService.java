package com.cy.news.api.service;

import com.cy.news.pojo.DTO.ResultDTO;
import com.cy.news.pojo.User;
import com.cy.news.pojo.VO.UserNameLoginVO;

import java.util.List;

/**
 * @ClassName UserService
 * @Author 6yi
 * @Date 2020/11/19 20:13
 * @Version 1.0
 * @Description:
 */

public interface UserService {



    ResultDTO login(UserNameLoginVO userVO);
  //  ResultDTO register(User user);

    ResultDTO updateUserStatus(Integer userStatusCode,Integer userId);

   // List<User> findAllUsers();

}
