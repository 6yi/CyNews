package com.cy.news.pojo.Exception;

import com.cy.news.pojo.DTO.ResultDTO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName e
 * @Author 6yi
 * @Date 2020/11/23 19:29
 * @Version 1.0
 * @Description:
 */


@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = RuntimeException.class)
    public ResultDTO error(RuntimeException e){
        
        return ResultDTO.builder().code(500).data("网络连接异常").build();

    }

}
