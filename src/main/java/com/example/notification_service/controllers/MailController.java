package com.example.notification_service.controllers;

import com.example.core.dto.UserEventDTO;
import com.example.notification_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class MailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody UserEventDTO dto) {
        try {
            emailService.sendNotification(
                    new UserEventDTO(dto.getEmail(), dto.getOperation())
            );
            return ResponseEntity.ok("Письмо отправлено");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка: " + e.getMessage());
        }
    }

}