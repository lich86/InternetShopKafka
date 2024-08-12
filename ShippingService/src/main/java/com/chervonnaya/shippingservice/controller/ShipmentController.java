package com.chervonnaya.shippingservice.controller;

import com.chervonnaya.orderdto.OrderDTO;
import com.chervonnaya.shippingservice.dto.ShipmentDTO;
import com.chervonnaya.shippingservice.model.Shipment;
import com.chervonnaya.shippingservice.service.ShipmentService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/shipment")
public class ShipmentController {

    private final ShipmentService service;
    private final KafkaTemplate<String, OrderDTO> kafkaProducerTemplate;

    @PostMapping
    public Shipment updateShipment(@RequestBody ShipmentDTO dto) {
        try {
            Shipment shipment = service.update(dto);
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(shipment.getOrderId());
            orderDTO.setEmail(shipment.getEmail());
            orderDTO.setStatus(String.valueOf(shipment.getStatus()));
            kafkaProducerTemplate.send("shipped_orders", orderDTO);
            return shipment;
        } catch (Exception e) {
            throw new RuntimeException("Unable to update shipment");
        }
    }
}
