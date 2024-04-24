package com.hardwarestore.paymentservice.controller;

import com.hardwarestore.paymentservice.payload.request.PaymentRequest;
import com.hardwarestore.paymentservice.payload.response.PaymentResponse;
import com.hardwarestore.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest) {
        log.info("PaymentController | doPayment is called");

        log.info("PaymentController | doPayment | paymentRequest : {}", paymentRequest.toString());

        return new ResponseEntity<>(paymentService.doPayment(paymentRequest), HttpStatus.OK);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentDetailsByOrderId(@PathVariable("orderId") Long orderId) {
        log.info("PaymentController | getPaymentDetailsByOrderId is called");
        log.info("PaymentController | doPayment | orderId : {}", orderId);

        return new ResponseEntity<>(paymentService.getPaymentDetailsByOrderId(orderId), HttpStatus.OK);
    }


}
