package com.cy.news.userserver.Controller;

import cn.hutool.core.lang.Validator;
import cn.hutool.crypto.SecureUtil;
import com.cy.news.api.service.UserService;
import com.cy.news.pojo.DTO.ResultDTO;
import com.cy.news.pojo.Exception.UserRetErrorCode;
import com.cy.news.pojo.VO.UserNameLoginVO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    private final static Logger logger= LoggerFactory.getLogger(UserController.class);


    @DubboReference(version = "1.0.0")
    private UserService userService;

    @PostMapping("/login")
    public ResultDTO login(@RequestBody UserNameLoginVO userNameLoginVO){

        if(userNameLoginVO.getPassWord().length()<8||userNameLoginVO.getPassWord().length()>32){
            return ResultDTO.builder().code(UserRetErrorCode.UNAME_OR_PWD_LEN_ERROR).data("用户名或密码长度不符").build();
        }
        
        userNameLoginVO.setPassWord(SecureUtil.md5(userNameLoginVO.getPassWord()));


        return  userService.login(userNameLoginVO);

    }


}
