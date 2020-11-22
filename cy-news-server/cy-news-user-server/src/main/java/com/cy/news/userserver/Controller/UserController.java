package com.cy.news.userserver.Controller;

import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.core.lang.Validator;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import com.cy.news.api.service.UserService;
import com.cy.news.pojo.DTO.ResultDTO;
import com.cy.news.pojo.Exception.RegisterRetErrorCode;
import com.cy.news.pojo.Exception.UserRetErrorCode;
import com.cy.news.pojo.User;
import com.cy.news.pojo.Utils.RegisterUtils;
import com.cy.news.pojo.VO.RegisterUserByEmailVO;

import com.cy.news.pojo.VO.UserNameLoginVO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

            userNameLoginVO.setPassWord(SecureUtil.md5(userNameLoginVO.getPassWord()));

            return  userService.login(userNameLoginVO);

    }

    @PostMapping("/register")
    public ResultDTO  register(@RequestBody RegisterUserByEmailVO registerUserVO) {

        //验证registerUserVO是否有值为空
        if (registerUserVO == null) {
            return ResultDTO.builder().code(UserRetErrorCode.ERROR).build();
        }

        //验证长度跟邮箱格式
        if (registerUserVO.getUserName().length() < 8 || registerUserVO.getUserName().length() > 15) {
            return ResultDTO.builder().code(RegisterRetErrorCode.USERNAME_LENGTG_ERROR).data("用户名长度为8-15").build();
        } else if (registerUserVO.getNickName().length() < 3 || registerUserVO.getNickName().length() > 8) {
            return ResultDTO.builder().code(RegisterRetErrorCode.NICKNAME_LENGTG_ERROR).data("名称长度为3-8").build();
        } else if (registerUserVO.getPassWord().length() < 8 || registerUserVO.getPassWord().length() > 12) {
            return ResultDTO.builder().code(RegisterRetErrorCode.PASSWORD_LENGTG_ERROR).data("密码长度为8-32").build();
        } else if (Validator.isEmail(registerUserVO.getEmail())==false) {

            return ResultDTO.builder().code(RegisterRetErrorCode.EMAIL_ERROR).data("邮箱格式错误").build();
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
