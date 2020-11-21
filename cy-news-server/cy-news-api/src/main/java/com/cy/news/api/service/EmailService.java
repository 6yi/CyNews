package com.cy.news.api.service;
/**
 * @ClassName UserService
 * @Author yj
 * @Date 2020/11/19 20:13
 * @Version 1.0
 * @Description:
 */

import com.cy.news.pojo.DTO.ResultDTO;
import org.springframework.web.bind.annotation.RestController;


public interface EmailService {

        ResultDTO sendEmail(Integer id, String email);

}
