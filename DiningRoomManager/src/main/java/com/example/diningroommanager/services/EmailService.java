package com.example.diningroommanager.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements EmailSender {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSimpleEmail(String subject, String text, String from, String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public void sendSimpleEmail(String subject, String text, String from, String to, String ... bcc) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setBcc(bcc);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
