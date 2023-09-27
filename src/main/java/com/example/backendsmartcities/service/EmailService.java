package com.example.backendsmartcities.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
/**
 * Author: Badreddine TIRGANI
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;



    public void sendEmail(String to, String subject, String message) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
        mimeMessage.setContent(message, "text/html");
        helper.setTo(to);
        helper.setSubject(subject);
        javaMailSender.send(mimeMessage);
    }

}