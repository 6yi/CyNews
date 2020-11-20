package com.cy.news.userprovider.service;

import com.cy.news.api.service.UserService;
import com.cy.news.pojo.DTO.ResultDTO;
import com.cy.news.pojo.Exception.UserRetErrorCode;
import com.cy.news.pojo.User;
import com.cy.news.pojo.Exception.UserStatusCode;
import com.cy.news.pojo.VO.LoginSuccessVO;
import com.cy.news.pojo.VO.UserNameLoginVO;
import com.cy.news.userprovider.dao.UserDao;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserServiceImpl
 * @Author 6yi
 * @Date 2020/11/19 21:04
 * @Version 1.0
 * @Description:
 */

@DubboService(version = "1.0.0")
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;



    /**
     * @author 6yi
     * @date 2020/11/20
     * @return
     * @Description   用户登录
     *
     *  PS: 该服务不校验用户账户密码正确性 , 调用服务的消费者自行校验 !
     *  1.判断用户密码是否正确
     *  2.根据用户状态返回响应码
     **/
    @Override
    public ResultDTO login(UserNameLoginVO userVO) {

        User user = userDao.selectByUserName(userVO.getUserName());

        if(userVO.getPassWord().equals(user.getuPassword())){

            if (UserStatusCode.NOT_ACTIVATED.equals(user.getuStatus())){
                //未激活
                // todo 调用邮箱服务

                return ResultDTO.builder().code(UserRetErrorCode.NOT_ACTIVATED).build();
            }else if(UserStatusCode.BAN.equals(user.getuStatus())){

                return ResultDTO.builder().code(UserRetErrorCode.NOT_AUTHORITY).build();
            }else if(UserStatusCode.DELETED.equals(user.getuStatus())){

                return ResultDTO.builder().code(UserRetErrorCode.NOT_AUTHORITY).build();
            }else{
                //登录成功,抹除敏感信息
                //todo 签发JWT
                user.setuPassword(null);
                user.setuStatus(null);

                LoginSuccessVO loginSuccessVO = LoginSuccessVO.builder().user(user).JWT_TOKEN("jwt").build();
                return ResultDTO.builder().code(UserRetErrorCode.OK).data(loginSuccessVO).build();

            }

        }else {

            return ResultDTO.builder().code(UserRetErrorCode.PASS_WORD_ERROR).build();

        }


    }



}
