package com.cy.news.common.Exception;

import com.cy.news.common.DTO.ResultDTO;
import com.cy.news.common.Utils.ErrorMsgUtils;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(value = RuntimeException.class)
    public ResultDTO exceptionHandler(Exception e){
        log.warn(ErrorMsgUtils.getExceptionAllInfo(e));
        return ResultDTO.builder().code(500).data("请检查网络").build();
    }

}
