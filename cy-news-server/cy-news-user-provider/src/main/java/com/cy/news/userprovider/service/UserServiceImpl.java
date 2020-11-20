package com.cy.news.userprovider.service;

import com.cy.news.api.service.UserService;
import com.cy.news.pojo.DTO.ResultDTO;
import org.apache.dubbo.config.annotation.DubboService;
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


    @Override
    public ResultDTO login() {
        return ResultDTO.builder().code(200).data("dubbo调用成功+lz").build();
    }

}
