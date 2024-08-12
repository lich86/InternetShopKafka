package com.chervonnaya.orderservice.controller;


import com.chervonnaya.orderdto.OrderDTO;
import com.chervonnaya.orderservice.model.Order;
import com.chervonnaya.orderservice.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService service;
    private final KafkaTemplate<String, OrderDTO> kafkaProducerTemplate;

    @PostMapping
    public Order addOrder(@RequestBody OrderDTO dto) {
        Order order;
        try {
           order = service.save(dto);
           dto.setId(order.getId());
            kafkaProducerTemplate.send("new_orders", dto);
            return order;
        } catch (Exception e) {
            throw new RuntimeException("Unable to save order");
        }
    }
}
