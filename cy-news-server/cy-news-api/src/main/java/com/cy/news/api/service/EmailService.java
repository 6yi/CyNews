package com.cy.news.api.service;

import org.springframework.web.bind.annotation.RestController;


public interface EmailService {

        String SendEmail(Integer id,String email);

}
