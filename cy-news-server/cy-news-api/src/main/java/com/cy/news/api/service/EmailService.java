package com.cy.news.api.service;
/**
 * @ClassName UserService
 * @Author yj
 * @Date 2020/11/19 20:13
 * @Version 1.0
 * @Description:
 */

import com.cy.news.common.DTO.ResultDTO;


public interface EmailService {

        ResultDTO sendEmail(Integer id, String email);

}
