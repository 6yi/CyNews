package com.cy.news.userserver.Controller;

import com.cy.news.api.service.UserService;
import com.cy.news.pojo.DTO.ResultDTO;
import com.cy.news.pojo.VO.UserNameLoginVO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserController
 * @Author 6yi
 * @Date 2020/11/20 13:53
 * @Version 1.0
 * @Description:
 */

@RestController
public class UserController {

    @DubboReference(version = "1.0.0")
    private UserService userService;

    @GetMapping("/login")
    public ResultDTO login(UserNameLoginVO userNameLoginVO){


        return  userService.login(userNameLoginVO);

    }


}
