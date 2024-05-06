package com.hardwarestore.orderservice.service.impl;

import com.hardwarestore.orderservice.exception.OrderServiceCustomException;
import com.hardwarestore.orderservice.external.client.PaymentService;
import com.hardwarestore.orderservice.external.client.ProductService;
import com.hardwarestore.orderservice.mapper.OrderMapper;
import com.hardwarestore.orderservice.model.Order;
import com.hardwarestore.orderservice.payload.request.OrderRequest;
import com.hardwarestore.orderservice.payload.request.PaymentRequest;
import com.hardwarestore.orderservice.payload.response.OrderResponse;
import com.hardwarestore.orderservice.payload.response.PaymentResponse;
import com.hardwarestore.orderservice.payload.response.ProductResponse;
import com.hardwarestore.orderservice.repository.OrderRepository;
import com.hardwarestore.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
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

        log.info("Calling productService through FeignClient");
        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());
        log.info("done calling productService through FeignClient");

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
    public OrderResponse getOrderDetails(Long orderId, String bearerToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", bearerToken);

        HttpEntity<String> request = new HttpEntity<String>(headers);

        log.info("OrderServiceImpl | getOrderDetails | Get order details for Order Id : {}", orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(
                        () -> new OrderServiceCustomException("Order not found for the order id: " + orderId, "NOT_FOUND", 404)
                );

        log.info("OrderServiceImpl | getOrderDetails | Invoking Product service to fetch the product for id: {}", order.getProductId());

        ResponseEntity<ProductResponse> productResponseEntity = restTemplate.exchange(
                "http://PRODUCT-SERVICE/product/" + order.getProductId(), HttpMethod.GET, request, ProductResponse.class
        );
        ProductResponse productResponse = productResponseEntity.getBody();

        log.info("OrderServiceImpl | getOrderDetails | Getting payment information form the payment Service");

        ResponseEntity<PaymentResponse> paymentResponseEntity
                = restTemplate.exchange(
                "http://PAYMENT-SERVICE/payment/order/" + order.getId(),
                HttpMethod.GET,
                request,
                PaymentResponse.class
        );

        PaymentResponse paymentResponse = paymentResponseEntity.getBody();

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
