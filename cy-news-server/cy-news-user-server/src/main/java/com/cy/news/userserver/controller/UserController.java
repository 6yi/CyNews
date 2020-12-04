package com.cy.news.userserver.controller;

import cn.hutool.core.lang.Validator;
import cn.hutool.crypto.SecureUtil;
import com.cy.news.api.service.UserService;
import com.cy.news.common.DTO.ResultDTO;
import com.cy.news.common.Exception.UserRetErrorCode;
import com.cy.news.common.Exception.RegisterRetErrorCode;
import com.cy.news.common.Utils.RegisterUtils;
import com.cy.news.common.VO.RegisterUserByEmailVO;
import com.cy.news.common.VO.UserNameLoginVO;
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


        if(userNameLoginVO.getPassWord().length()<3||
                userNameLoginVO.getPassWord().length()>32||
                userNameLoginVO.getUserName().length()<5||
                userNameLoginVO.getUserName().length()>18){
            return ResultDTO.builder().code(UserRetErrorCode.UNAME_OR_PWD_LEN_ERROR).data("用户名或密码长度不符").build();
        }
        
        userNameLoginVO.setPassWord(SecureUtil.md5(userNameLoginVO.getPassWord()));


        return  userService.login(userNameLoginVO);
        
    }



    @PostMapping("/register")
    public ResultDTO  register(@RequestBody RegisterUserByEmailVO registerUserVO) {

        //验证registerUserVO是否有值为空
        if (registerUserVO == null||
                registerUserVO.getUserName()==null||
                registerUserVO.getNickName()==null||
                registerUserVO.getPassWord()==null||
                registerUserVO.getEmail()==null) {
            return ResultDTO.builder().code(UserRetErrorCode.ERROR).data("参数错误").build();
        }

        //验证长度跟邮箱格式
        if (registerUserVO.getUserName().length() < 8 ||
                registerUserVO.getUserName().length() > 15) {
            return ResultDTO.builder().code(RegisterRetErrorCode.USERNAME_LENGTG_ERROR).data("用户名长度为8-15").build();
        } else if (registerUserVO.getNickName().length() < 3 ||
                registerUserVO.getNickName().length() > 8) {
            return ResultDTO.builder().code(RegisterRetErrorCode.NICKNAME_LENGTG_ERROR).data("名称长度为3-8").build();
        } else if (registerUserVO.getPassWord().length() < 8 ||
                registerUserVO.getPassWord().length() > 12) {
            return ResultDTO.builder().code(RegisterRetErrorCode.PASSWORD_LENGTG_ERROR).data("密码长度为8-32").build();
        } else if (!Validator.isEmail(registerUserVO.getEmail())) {
            return ResultDTO.builder().code(RegisterRetErrorCode.EMAIL_ERROR).data("邮箱格式错误").build();
        } else if (!registerUserVO.getPassWord().equals(registerUserVO.getConfirmPassword())) {
            return ResultDTO.builder().code(RegisterRetErrorCode.PASSWORD_DIFFERENT).data("密码输入不一致").build();
        }

        if(RegisterUtils.isSpecialChar(registerUserVO.getUserName())||
                                       RegisterUtils.isSpecialChar(registerUserVO.getPassWord())||
                                       Validator.hasChinese(registerUserVO.getUserName())||
                                       Validator.hasChinese(registerUserVO.getPassWord())
        ){
            return ResultDTO.builder().code(RegisterRetErrorCode.ISSPECIALCHAR_ERROR).data("用户名或者密码不能带有特殊字符").build();
        }

            registerUserVO.setPassWord(SecureUtil.md5(registerUserVO.getPassWord()));

            return userService.register(registerUserVO);
    }
}
