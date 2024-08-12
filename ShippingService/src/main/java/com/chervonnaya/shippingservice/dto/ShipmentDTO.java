package com.chervonnaya.shippingservice.dto;

import com.chervonnaya.shippingservice.model.Shipment.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentDTO {
    private Long orderId;
    private Status status;
}
