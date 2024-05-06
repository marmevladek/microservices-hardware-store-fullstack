package com.hardwarestore.orderservice.service;

import com.hardwarestore.orderservice.payload.request.OrderRequest;
import com.hardwarestore.orderservice.payload.response.OrderResponse;

public interface OrderService {

    Long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(Long orderId, String bearerToken);
}
