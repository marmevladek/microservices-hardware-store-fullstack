package com.hardwarestore.orderservice.mapper;

import com.hardwarestore.orderservice.model.Order;
import com.hardwarestore.orderservice.payload.response.OrderResponse;

public class OrderMapper {

    public static OrderResponse mapToOrderResponse(Order order, OrderResponse.ProductDetails productDetails, OrderResponse.PaymentDetails paymentDetails) {
        return new OrderResponse(
                order.getId(),
                order.getOrderDate(),
                order.getOrderStatus(),
                order.getAmount(),
                productDetails,
                paymentDetails
        );
    }
}
