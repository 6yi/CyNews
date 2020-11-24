package com.cy.news.emailserver.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Component
public class EmailUtils{

    @Autowired
    private JavaMailSender mailSender;


    public Integer send(String sender,String receiver,String title,String text){

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(sender);

        message.setCc(sender);

        message.setTo(receiver);

        message.setSubject(title);

        message.setText(text);
        try {
            mailSender.send(message);
            return 1;
        }catch (Exception e){
            return 0;
        }


    }


}