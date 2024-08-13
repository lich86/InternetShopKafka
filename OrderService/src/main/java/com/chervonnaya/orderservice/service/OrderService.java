package com.chervonnaya.orderservice.service;

import com.chervonnaya.orderdto.OrderDTO;
import com.chervonnaya.orderservice.model.Order;
import com.chervonnaya.orderservice.repository.OrderRepository;
import com.chervonnaya.orderservice.service.mappers.OrderMapper;
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
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;

    @Transactional
    public Order save(OrderDTO dto) {
        try {
            Order order = repository.save(mapper.map(dto));
            log.info(String.format("Saved order, id: %d", order.getId()));
            return order;
        } catch (Exception ex) {
            log.error("Unable to save new order");
            throw new RuntimeException("Unable to save order");
        }
    }

    @KafkaListener(topics = {"paid_orders", "shipped_orders"})
    @Transactional
    protected void updatePayment(OrderDTO orderDTO, Acknowledgment acknowledgment) {
        Long orderId = orderDTO.getId();
        Order order = repository.findById(orderId)
            .orElseThrow(() -> new EntityNotFoundException(String.valueOf(orderId)));
        try {
            order.setStatus(order.getStatus());
            log.info(String.format("Updated payment, id: %s. Set status: %s",
                orderId, orderDTO.getStatus()));
            repository.save(order);
            acknowledgment.acknowledge();
        } catch (Exception e) {
            throw new RuntimeException(String.format("Unable to update order, id: %s",
                orderId));
        }
    }

}
