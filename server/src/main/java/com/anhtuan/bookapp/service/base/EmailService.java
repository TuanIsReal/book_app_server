package com.anhtuan.bookapp.service.base;

public interface EmailService {
    void sendEmail(String to, String subject, String text);
}
