package com.chervonnaya.orderservice.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @CollectionTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id"))
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "product")
    private List<String> products;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "email")
    private String email;

    @Column(name = "sum")
    private BigDecimal sum;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    public enum Status {
        VIEWED,
        REJECTED,
        IN_PROGRESS,
        PENDING,
        READY_FOR_DISPATCH,
        DISPATCHED,
        SHIPPED,
        DELIVERED,
        INVOICED,
        PARTIALLY_PAID,
        PAID,
        RETURNED,
        CLOSED,
    }
}
