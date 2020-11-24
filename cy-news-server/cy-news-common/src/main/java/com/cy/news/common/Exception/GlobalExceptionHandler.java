package com.cy.news.common.Exception;

import com.cy.news.common.DTO.ResultDTO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName GlobalExceptionHandler
 * @Author 6yi
 * @Date 2020/11/24 16:42
 * @Version 1.0
 * @Description:
 */

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value =RuntimeException.class)
    public ResultDTO exceptionHandler(Exception e){
        return ResultDTO.builder().code(500).data(e.getMessage()).build();
    }


}
