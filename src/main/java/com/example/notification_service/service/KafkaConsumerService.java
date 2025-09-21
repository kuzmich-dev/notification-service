package com.example.notification_service.service;

import com.example.core.dto.UserEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final EmailService emailService;

    @KafkaListener(topics = "user-events", groupId = "notification-group")
    public void consume(UserEventDTO event) {
        System.out.println("Получено событие: " + event);

        try {
            emailService.sendNotification(event);
        } catch (Exception e) {
            System.err.println("Ошибка при обработке события: " + e.getMessage());
            e.printStackTrace();
        }
    }

}