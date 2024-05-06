package com.hardwarestore.orderservice.controller;

import com.hardwarestore.orderservice.payload.request.OrderRequest;
import com.hardwarestore.orderservice.payload.response.OrderResponse;
import com.hardwarestore.orderservice.service.OrderService;
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

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable long orderId, @RequestHeader("Authorization") String bearerToken) {
        OrderResponse orderResponse = orderService.getOrderDetails(orderId, bearerToken);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

}
