package com.chervonnaya.shippingservice.service;

import com.chervonnaya.orderdto.OrderDTO;
import com.chervonnaya.shippingservice.dto.ShipmentDTO;
import com.chervonnaya.shippingservice.model.Shipment;
import com.chervonnaya.shippingservice.repository.ShipmentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
@Slf4j
public class ShipmentService {

    private final ShipmentRepository repository;

    @KafkaListener(topics = "paid_orders")
    private void save(OrderDTO dto, Acknowledgment acknowledgment) {
        try {
            Shipment shipment = new Shipment();
            shipment.setOrderId(dto.getId());
            shipment.setAddress(dto.getAddress());
            shipment.setEmail(dto.getEmail());
            shipment.setStatus(Shipment.Status.valueOf(dto.getStatus()));
            shipment.setShippingCost(calculateShippingCost());
            repository.save(shipment);
            log.info(String.format("Saved new shipment, id: %d", shipment.getOrderId()));
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error(String.format("Unable to save new shipment, orderId: %d", dto.getId()));
            throw new RuntimeException(String.format("Unable to save new shipment, orderId: %d", dto.getId()));
        }
    }

    private BigDecimal calculateShippingCost() {
        return new BigDecimal(Math.random() * 100);
    }

    public Shipment update(ShipmentDTO dto) {
        Long id = dto.getOrderId();
        Shipment shipment = repository.findByOrderId(id)
            .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        try {
            shipment.setStatus(dto.getStatus());
            repository.save(shipment);
            log.info("Updated shipment, id: {}. Set status: {}",
                shipment.getOrderId(), shipment.getStatus());
            return shipment;
        } catch (Exception e) {
            log.error(String.format("Unable to update shipment, id: %d", dto.getOrderId()));
            throw new RuntimeException(String.format("Unable to update shipment, id: %d", dto.getOrderId()));
        }
    }
}
