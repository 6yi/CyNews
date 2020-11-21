package com.cy.news.emailserver.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("serdbyemail")
public class EmailUtils{

    @Autowired

    private JavaMailSender mailSender;

    public String send(String sender,String receiver,String title,String text){
        SimpleMailMessage message = new SimpleMailMessage();


        message.setFrom("yj979399417@163.com");
        message.setCc("yj979399417@163.com");

        message.setTo("lzhengycy@foxmail.com");

        message.setSubject("it is a test for spring boot");

        message.setText("你好 我正在测试发送邮件。");
        mailSender.send(message);
        return "1";
    }


}