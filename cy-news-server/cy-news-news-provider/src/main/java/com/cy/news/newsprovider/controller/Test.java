package com.cy.news.newsprovider.controller;

import com.baomidou.mybatisplus.annotation.TableId;
import com.cy.news.common.DTO.ResultDTO;
import com.cy.news.newsprovider.service.NewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName Test
 * @Author 6yi
 * @Date 2020/11/27 12:21
 * @Version 1.0
 * @Description:
 */

@RestController
public class Test {

    @Autowired
    NewsServiceImpl newsService;



    @GetMapping("/getComment/{page}/{number}")
    public ResultDTO getComment(@PathVariable("page") Integer page,@PathVariable("number") Integer number){
        return null;
    }



}
