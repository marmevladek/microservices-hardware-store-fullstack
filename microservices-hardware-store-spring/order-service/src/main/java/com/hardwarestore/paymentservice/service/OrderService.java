package com.hardwarestore.paymentservice.service;

import com.hardwarestore.paymentservice.payload.request.OrderRequest;
import com.hardwarestore.paymentservice.payload.response.OrderResponse;

public interface OrderService {

    Long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(Long orderId, String bearerToken);
}
