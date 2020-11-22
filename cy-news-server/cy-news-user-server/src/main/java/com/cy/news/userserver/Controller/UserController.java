package com.cy.news.userserver.Controller;

import com.cy.news.api.service.UserService;
import com.cy.news.pojo.DTO.ResultDTO;
import com.cy.news.pojo.Exception.UserRetErrorCode;
import com.cy.news.pojo.User;
import com.cy.news.pojo.VO.UserNameLoginVO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/login")
    public ResultDTO login(UserNameLoginVO userNameLoginVO){

        logger.info(userNameLoginVO.toString());
        return  userService.login(userNameLoginVO.builder().userName("123").passWord("asd").build());

    }

//    @GetMapping("/register")
//    public ResultDTO  register(User user){
//        List<User> users=userService.findAllUsers();
//        if(user==null){
//            return ResultDTO.builder().code(UserRetErrorCode.ERROR).build();
//        }else if(user.getuEmail().isEmpty()&&user.getuUsername().isEmpty()&&user.getuNickname().isEmpty()&&user.getuPassword().isEmpty()){
//           return ResultDTO.builder().code(UserRetErrorCode.ISEMPTY_ERROR).build();
//        }
//        for (User u1:users
//             ) {
//            if(u1.getuUsername().equals(user.getuUsername())){
//                return ResultDTO.builder().code(UserRetErrorCode.USERNAME_ERROR).build();
//            }else if(u1.getuEmail().equals(user.getuEmail())){
//                return ResultDTO.builder().code(UserRetErrorCode.MAIL_ERROR).build();
//            }
//        }
//        return userService.register(user);
//    }


}
