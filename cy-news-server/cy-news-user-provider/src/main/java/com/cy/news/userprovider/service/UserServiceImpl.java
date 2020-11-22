package com.cy.news.userprovider.service;

import com.cy.news.api.service.EmailService;
import com.cy.news.api.service.UserService;
import com.cy.news.pojo.DTO.ResultDTO;
import com.cy.news.pojo.Exception.UserRetErrorCode;
import com.cy.news.pojo.User;
import com.cy.news.pojo.Exception.UserStatusCode;
import com.cy.news.pojo.Utils.JWTUtils;
import com.cy.news.pojo.VO.LoginSuccessVO;
import com.cy.news.pojo.VO.UserNameLoginVO;
import com.cy.news.userprovider.dao.UserDao;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private final static Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserDao userDao;

    @DubboReference(version = "1.0.0")
    EmailService emailService;




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
        logger.info(userVO.toString());
        User user = userDao.selectByUserName(userVO.getUserName());

        if(user!=null&&userVO.getPassWord().equals(user.getuPassword())){

            if (UserStatusCode.NOT_ACTIVATED.equals(user.getuStatus())){
                //未激活
                // todo 调用邮箱服务
                emailService.sendEmail(user.getuId(),user.getuEmail());
                return ResultDTO.builder().code(UserRetErrorCode.NOT_ACTIVATED).build();
            }else if(UserStatusCode.BAN.equals(user.getuStatus())){

                return ResultDTO.builder().code(UserRetErrorCode.NOT_AUTHORITY).build();
            }else if(UserStatusCode.DELETED.equals(user.getuStatus())){

                return ResultDTO.builder().code(UserRetErrorCode.NOT_AUTHORITY).build();
            }else{
                //登录成功,抹除敏感信息
                user.setuPassword(null);
                user.setuStatus(null);

                //todo 签发JWT
                String jwtString = JWTUtils.userLoginJwtString(user);
                LoginSuccessVO loginSuccessVO = LoginSuccessVO.builder().user(user).JWT_TOKEN(jwtString).build();
                return ResultDTO.builder().code(UserRetErrorCode.OK).data(loginSuccessVO).build();

            }

        }else {

            return ResultDTO.builder().code(UserRetErrorCode.PASS_WORD_ERROR).build();

        }
    }

//    @Override
//    public ResultDTO register(User user) {
//
//        user.setuStatus(0);
//        userDao.insert(user);
//        User user1=userDao.selectByUserName(user.getuUsername());
//        emailService.sendEmail(user.getuId(),user.getuEmail());
//        return ResultDTO.builder().code(UserRetErrorCode.OK).build();
//    }

    @Override
    public ResultDTO updateUserStatus(Integer userStatusCode,Integer userId) {
        try {
            userDao.updateUserStatusById(userStatusCode,userId);
            return ResultDTO.builder().code(UserRetErrorCode.OK).build();
        }catch (Exception e){
            return ResultDTO.builder().code(UserRetErrorCode.ERROR).build();
        }

    }
//
//    @Override
//    public List<User> findAllUsers() {
//        return userDao.findAllUsers();
//    }


}
