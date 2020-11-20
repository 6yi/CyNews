package com.cy.news.api.service;

import com.cy.news.pojo.DTO.ResultDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName UserService
 * @Author 6yi
 * @Date 2020/11/19 20:13
 * @Version 1.0
 * @Description:
 */
@FeignClient("userProvider")
public interface UserService {


    @GetMapping("/user/login")
    ResultDTO login();


}
