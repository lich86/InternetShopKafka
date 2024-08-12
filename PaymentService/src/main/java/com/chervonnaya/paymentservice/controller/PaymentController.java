package com.chervonnaya.paymentservice.controller;

import com.chervonnaya.orderdto.OrderDTO;
import com.chervonnaya.paymentservice.dto.PaymentDTO;
import com.chervonnaya.paymentservice.model.Payment;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chervonnaya.paymentservice.service.PaymentService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService service;
    private final KafkaTemplate<String, OrderDTO> kafkaTemplate;

    @PostMapping
    public Payment updatePayment(@RequestBody PaymentDTO dto) {
        Payment payment;
        try {
            payment = service.update(dto);
            if((payment.getStatus()).equals(Payment.Status.PAID)) {
                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setId(dto.getOrderId());
                orderDTO.setCustomerId(payment.getCustomerId());
                orderDTO.setAddress(payment.getAddress());
                orderDTO.setEmail(payment.getEmail());
                orderDTO.setStatus(String.valueOf(payment.getStatus()));
                kafkaTemplate.send("paid_orders", orderDTO);
            }
            return payment;
        } catch (Exception e) {
            throw new RuntimeException(String.format("Unable to update payment with order id: %d", dto.getOrderId()));
        }
    }
}
