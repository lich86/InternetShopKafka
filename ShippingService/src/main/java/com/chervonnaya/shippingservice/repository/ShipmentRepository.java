package com.chervonnaya.shippingservice.repository;

import com.chervonnaya.shippingservice.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    @Query("SELECT s FROM Shipment s WHERE s.orderId = :orderId")
    Optional<Shipment> findByOrderId(Long orderId);
}
