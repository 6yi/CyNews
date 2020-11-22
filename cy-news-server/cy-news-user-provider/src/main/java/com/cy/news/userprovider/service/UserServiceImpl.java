package com.cy.news.userprovider.service;

import com.cy.news.api.service.EmailService;
import com.cy.news.api.service.UserService;
import com.cy.news.pojo.DTO.ResultDTO;
import com.cy.news.pojo.Exception.UserRetErrorCode;
import com.cy.news.pojo.MQ.EmailMQEntity;
import com.cy.news.pojo.User;
import com.cy.news.pojo.Exception.UserStatusCode;
import com.cy.news.pojo.Utils.JWTUtils;
import com.cy.news.pojo.VO.LoginSuccessVO;
import com.cy.news.pojo.VO.RegisterUserByEmailVO;
import com.cy.news.pojo.VO.UserNameLoginVO;
import com.cy.news.userprovider.dao.UserDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    private UserDao userDao;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Value("${mq.user.topic}")
    private String topic;

    @Value("${mq.user.tag.send}")
    private String tag;

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
                //异步发送至消息队列
                sendEmailMQ(user.getuId(),
                        EmailMQEntity
                                .builder()
                                .uId(user.getuId())
                                .email(user.getuEmail())
                                .build());

                return ResultDTO.builder().code(UserRetErrorCode.NOT_ACTIVATED).data("未激活邮箱").build();
            }else if(UserStatusCode.BAN.equals(user.getuStatus())){

                return ResultDTO.builder().code(UserRetErrorCode.NOT_AUTHORITY).data("账户已封禁").build();
            }else if(UserStatusCode.DELETED.equals(user.getuStatus())){

                return ResultDTO.builder().code(UserRetErrorCode.NOT_AUTHORITY).data("账户已封禁").build();
            }else{
                //登录成功,抹除敏感信息
                user.setuPassword(null);
                user.setuStatus(null);

                String jwtString = JWTUtils.userLoginJwtString(user);
                LoginSuccessVO loginSuccessVO = LoginSuccessVO.builder().user(user).JWT_TOKEN(jwtString).build();
                return ResultDTO.builder().code(UserRetErrorCode.OK).data(loginSuccessVO).build();
            }

        }else {

            return ResultDTO.builder().code(UserRetErrorCode.PASS_WORD_ERROR).data("账户或密码错误").build();

        }
    }


    @Override
    public ResultDTO register(RegisterUserByEmailVO registerUserVO) {

        if(userDao.selectByUserName(registerUserVO.getUserName())!=null){
            return ResultDTO.builder().code(UserRetErrorCode.REGISTER_ERROR).data("用户名已存在").build();
        }
        if(userDao.findUserByMail(registerUserVO.getEmail())!=null){
            return ResultDTO.builder().code(UserRetErrorCode.REGISTER_ERROR).data("该邮箱已被注册").build();
        }

        User user= null;

        try {

            user = User.builder()
                    .uUsername(registerUserVO.getUserName())
                    .uPassword(registerUserVO.getPassWord())
                    .uNickname(registerUserVO.getNickName())
                    .uEmail(registerUserVO.getEmail())
                    .uStatus(0)
                    .uDate(new Date())
                    .build();

             userDao.insert(user);

        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResultDTO.builder().code(UserRetErrorCode.REGISTER_ERROR).data("未知错误,请联系管理员").build();

        }

        sendEmailMQ(user.getuId(), EmailMQEntity.builder().uId(user.getuId()).email(user.getuEmail()).build());
        return ResultDTO.builder().code(UserRetErrorCode.OK).build();
    }

    /**
     * @author 6yi
     * @date 2020/11/22
     * @return
     * @Description 异步发送消息
     **/
    private void sendEmailMQ(Integer id, EmailMQEntity entity){
        try {
            Message message = new Message(topic, tag, id.toString(), new ObjectMapper().writeValueAsString(entity).getBytes());
            logger.info("消息正在写入");
            rocketMQTemplate.getProducer().send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    logger.info("消息写入成功");
                }

                @Override
                public void onException(Throwable throwable) {
                    logger.info("消息写入失败");
                }
            });
        } catch (Exception e) {
            logger.info("消息写入失败");
            e.printStackTrace();
        }
    }




    @Override
    public ResultDTO updateUserStatus(Integer userStatusCode,Integer userId) {
        try {
            userDao.updateUserStatusById(userStatusCode,userId);

            return ResultDTO.builder().code(UserRetErrorCode.OK).build();
        }catch (Exception e){
            return ResultDTO.builder().code(UserRetErrorCode.ERROR).build();
        }

    }




}
