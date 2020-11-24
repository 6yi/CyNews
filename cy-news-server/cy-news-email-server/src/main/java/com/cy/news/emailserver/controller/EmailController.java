package com.cy.news.emailserver.controller;

import cn.hutool.core.convert.Convert;
import com.cy.news.api.service.UserService;
import com.cy.news.common.DTO.ResultDTO;
import com.cy.news.common.Exception.EmailRetErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class EmailController {

    @Autowired
    StringRedisTemplate redisTemplate;

    @DubboReference(version = "1.0.0")
    UserService userService;


    @GetMapping("/activation")
    public ResultDTO activation(@RequestParam() String id, @RequestParam() String code){

        String saveCode= redisTemplate.opsForValue().get("mailServer-sendCode:"+id);

        if(saveCode==null){
            log.info("code is null");
            return ResultDTO.builder().code(EmailRetErrorCode.ERROR).data("不存在该激活码或已过期").build();
        }else if(code.equals(saveCode)){

            //todo 调用用户服务更改激活状态

           userService.updateUserStatus(1,Convert.toInt(id));

           redisTemplate.delete("mailServer-sendCode:"+id);

           return ResultDTO.builder().code(EmailRetErrorCode.OK).data("激活成功").build();
        }
        log.info("code error");
        return ResultDTO.builder().code(EmailRetErrorCode.ERROR).data("激活失败").build();
    }


}
