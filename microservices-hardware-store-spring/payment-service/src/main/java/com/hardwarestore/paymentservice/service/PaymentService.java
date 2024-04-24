package com.hardwarestore.paymentservice.service;

import com.hardwarestore.paymentservice.payload.request.PaymentRequest;
import com.hardwarestore.paymentservice.payload.response.PaymentResponse;

public interface PaymentService {

    Long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(Long orderId);
}
