package ru.itis.uzel.service;

public interface MailService {
    void sendEmailForConfirm(String email, String code);
}