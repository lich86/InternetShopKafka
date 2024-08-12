package com.chervonnaya.orderservice.repository;

import com.chervonnaya.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
