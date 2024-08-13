package com.chervonnaya.paymentservice.service;

import com.chervonnaya.orderdto.OrderDTO;
import com.chervonnaya.paymentservice.dto.PaymentDTO;
import com.chervonnaya.paymentservice.model.Payment;
import com.chervonnaya.paymentservice.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository repository;

    @KafkaListener(topics = "new_orders")
    @Transactional
    protected void save(OrderDTO dto, Acknowledgment acknowledgment) {
        try {
            Payment payment = new Payment();
            payment.setOrderId(dto.getId());
            payment.setCustomerId(dto.getCustomerId());
            payment.setEmail(dto.getEmail());
            payment.setAmount(dto.getSum());
            payment.setAddress(dto.getAddress());
            payment.setStatus(Payment.Status.INITIALIZED);
            repository.save(payment);
            log.info("Saved new payment, id: {}",
                payment.getOrderId());
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("Unable to save new payment, orderId: {}", dto.getId());
            throw new RuntimeException(String.format("Unable to save new payment, orderId: %d", dto.getId()));
        }
    }

    @Transactional
    public Payment update(PaymentDTO dto) {
        Payment payment = repository.findByOrderId(dto.getOrderId())
            .orElseThrow(() -> new EntityNotFoundException(String.valueOf(dto.getOrderId())));
        try {
            payment.setStatus(dto.getStatus());
            repository.save(payment);
            log.info("Updated payment, id: {}. Set status: {}",
                payment.getOrderId(), payment.getStatus());
            return payment;
        } catch (Exception ex) {
            log.error(String.format("Unable to update payment, id: %d", dto.getOrderId()));
            throw new RuntimeException(String.format("Unable to update payment, id: %d", dto.getOrderId()));
        }
    }
}
