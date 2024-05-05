package com.hardwarestore.paymentservice.controller;

import com.hardwarestore.paymentservice.payload.request.OrderRequest;
import com.hardwarestore.paymentservice.payload.response.OrderResponse;
import com.hardwarestore.paymentservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/placeorder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest) {
        long orderId = orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }

    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable long orderId, @RequestHeader("Authorization") String bearerToken) {
        OrderResponse orderResponse = orderService.getOrderDetails(orderId, bearerToken);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

}
