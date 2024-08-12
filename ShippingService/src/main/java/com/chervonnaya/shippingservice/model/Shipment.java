package com.chervonnaya.shippingservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shipping")
public class Shipment {
    @Id
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private Status status;

    @Column(name = "shipping_cost")
    private BigDecimal shippingCost;


    public enum Status {
        PAID,
        SHIPPED,
        DELIVERED
    }
}
