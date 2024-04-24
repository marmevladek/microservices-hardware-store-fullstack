package com.hardwarestore.paymentservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "order_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="product_id")
    private Long productId;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name="order_date")
    private Instant orderDate;

    @Column(name="order_status")
    private String orderStatus;

    @Column(name="total_amount")
    private long amount;


}
