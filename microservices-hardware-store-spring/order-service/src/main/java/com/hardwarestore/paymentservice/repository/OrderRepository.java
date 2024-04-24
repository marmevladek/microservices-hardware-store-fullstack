package com.hardwarestore.paymentservice.repository;

import com.hardwarestore.paymentservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
