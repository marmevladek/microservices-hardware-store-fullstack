package com.hardwarestore.paymentservice.service.impl;

import com.hardwarestore.paymentservice.exception.OrderServiceCustomException;
import com.hardwarestore.paymentservice.external.client.PaymentService;
import com.hardwarestore.paymentservice.external.client.ProductService;
import com.hardwarestore.paymentservice.mapper.OrderMapper;
import com.hardwarestore.paymentservice.model.Order;
import com.hardwarestore.paymentservice.payload.request.OrderRequest;
import com.hardwarestore.paymentservice.payload.request.PaymentRequest;
import com.hardwarestore.paymentservice.payload.response.OrderResponse;
import com.hardwarestore.paymentservice.payload.response.PaymentResponse;
import com.hardwarestore.paymentservice.payload.response.ProductResponse;
import com.hardwarestore.paymentservice.repository.OrderRepository;
import com.hardwarestore.paymentservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final RestTemplate restTemplate;


    private final PaymentService paymentService;
    private final ProductService productService;

    @Override
    public Long placeOrder(OrderRequest orderRequest) {
        log.info("OrderServiceImpl | placeOrder | Placing Order Request orderRequest : {}", orderRequest.toString());
        log.info("OrderServiceImpl | placeOrder | Calling productService through FeignClient");

        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());

        log.info("OrderServiceImpl | placeOrder | Creating Order with Status CREATED");

        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();

        order = orderRepository.save(order);

        log.info("OrderServiceImpl | placeOrder | Calling Payment Service to complete the payment");

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getTotalAmount())
                .build();

        String orderStatus = null;

        try {
            paymentService.doPayment(paymentRequest);
            log.info("OrderServiceImpl | placeOrder | Payment done Successfully. Changing the Oder status to PLACED");
            orderStatus = "PLACED";
        } catch (Exception e) {
            log.error("OrderServiceImpl | placeOrder | Error occurred in payment. Changing order status to PAYMENT_FAILED");
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        order = orderRepository.save(order);

        log.info("OrderServiceImpl | placeOrder | Order Places successfully with Order Id: {}", order.getId());

        return order.getId();
    }



    @Override
    public OrderResponse getOrderDetails(Long orderId) {

        log.info("OrderServiceImpl | getOrderDetails | Get order details for Order Id : {}", orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(
                        () -> new OrderServiceCustomException("Order not found for the order id: " + orderId, "NOT_FOUND", 404)
                );

        log.info("OrderServiceImpl | getOrderDetails | Invoking Product service to fetch the product for id: {}", order.getProductId());

        ProductResponse productResponse = restTemplate.getForObject(
                "http://PRODUCT-SERVICE/product/" + order.getProductId(), ProductResponse.class
        );

        log.info("OrderServiceImpl | getOrderDetails | Getting payment information form the payment Service");

        PaymentResponse paymentResponse
                = restTemplate.getForObject(
                "http://PAYMENT-SERVICE/payment/order/" + order.getId(),
                PaymentResponse.class
        );

        OrderResponse.ProductDetails productDetails = OrderResponse.ProductDetails.builder()
                .productName(productResponse.getProductName())
                .productId(productResponse.getProductId())
                .build();

        OrderResponse.PaymentDetails paymentDetails = OrderResponse.PaymentDetails.builder()
                .paymentId(paymentResponse.getPaymentId())
                .paymentStatus(paymentResponse.getStatus())
                .paymentDate(paymentResponse.getPaymentDate())
                .paymentMode(paymentResponse.getPaymentMode())
                .build();

        return OrderMapper.mapToOrderResponse(order, productDetails, paymentDetails);
    }
}
