package com.hardwarestore.paymentservice.mapper;

import com.hardwarestore.paymentservice.model.Order;
import com.hardwarestore.paymentservice.payload.response.OrderResponse;

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
