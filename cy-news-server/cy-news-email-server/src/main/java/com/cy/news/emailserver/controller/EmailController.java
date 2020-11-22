package com.cy.news.emailserver.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.http.HtmlUtil;
import com.cy.news.api.service.UserService;
import com.cy.news.pojo.DTO.ResultDTO;
import com.cy.news.pojo.Exception.EmailRetErrorCode;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    StringRedisTemplate redisTemplate;

    @DubboReference(version = "1.0.0")
    UserService userService;


    @GetMapping("/activation")
    public ResultDTO activation(@RequestParam() String id, @RequestParam() String code){

        String saveCode= redisTemplate.opsForValue().get(id);
        HtmlUtil.unescape(saveCode);

        if(saveCode==null){
            return ResultDTO.builder().code(EmailRetErrorCode.ERROR).build();
        }else if(code.equals(saveCode)){

            //todo 调用用户服务更改激活状态
           userService.updateUserStatus(1,Convert.toInt(id));

           redisTemplate.delete(id);

           return ResultDTO.builder().code(EmailRetErrorCode.OK).build();
        }

        return ResultDTO.builder().code(EmailRetErrorCode.ERROR).build();


    }
}
