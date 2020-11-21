package com.cy.news.emailserver.controller;

import cn.hutool.http.HtmlUtil;
import com.cy.news.pojo.DTO.ResultDTO;
import com.cy.news.pojo.Exception.EmailRetErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping("/test")
    public String test(){
        return "test";
    }
    @GetMapping("/activation")
    public ResultDTO activation(String id, String code){

       String saveCode= redisTemplate.opsForValue().get(id);
        HtmlUtil.unescape(saveCode);

        if(saveCode==null){
            return ResultDTO.builder().code(EmailRetErrorCode.ERROR).build();
        }else if(code.equals(saveCode)){

            //todo 调用用户服务更改激活状态

            return ResultDTO.builder().code(EmailRetErrorCode.OK).build();
        }

        return ResultDTO.builder().code(EmailRetErrorCode.ERROR).build();


    }
}
