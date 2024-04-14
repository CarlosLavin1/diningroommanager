package com.example.diningroommanager.services;

public interface EmailSender {
    void sendSimpleEmail(String subject, String text, String from, String to);

    void sendSimpleEmail(String subject, String text, String from, String to, String ... bcc);
}
