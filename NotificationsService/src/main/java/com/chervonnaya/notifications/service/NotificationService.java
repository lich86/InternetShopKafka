package com.chervonnaya.notifications.service;

import com.chervonnaya.orderdto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

    @KafkaListener(topics = "shipped_orders")
    private void sendNotification(OrderDTO dto, Acknowledgment acknowledgment) {
        try {
            String email = dto.getEmail();
            sendEmail(email);
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("Unable to send message to {}", dto.getEmail());
        }
    }

    private void sendEmail(String email) {
        log.info("Email was sent to {}", email);
    }
}
