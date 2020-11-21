package com.cy.news.api.service;

import com.cy.news.pojo.DTO.ResultDTO;
import org.springframework.web.bind.annotation.RestController;


public interface EmailService {

        ResultDTO SendEmail(Integer id, String email);

}
