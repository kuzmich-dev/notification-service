package com.example.notification_service.service;

import com.example.core.dto.UserEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;


    public void sendNotification(UserEventDTO event) {
        if (event == null || event.getEmail() == null || event.getOperation() == null) {
            System.err.println("Некорректные данные: " + event);
            return;
        }
        try {
            String subject = switch (event.getOperation()) {
                case "CREATE" -> "Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан.";
                case "DELETE" -> "Здравствуйте! Ваш аккаунт был удалён.";
                default -> "Уведомление";
            };

            String body = "Операция: " + event.getOperation();

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(event.getEmail());
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
        }catch (Exception e){
            System.err.println("Ошибка при отправке письма: " + e.getMessage());
            e.printStackTrace();
        }
    }

}