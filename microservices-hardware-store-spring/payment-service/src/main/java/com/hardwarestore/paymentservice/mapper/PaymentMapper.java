package com.hardwarestore.paymentservice.mapper;

import com.hardwarestore.paymentservice.model.TransactionDetails;
import com.hardwarestore.paymentservice.payload.response.PaymentResponse;
import com.hardwarestore.paymentservice.utils.PaymentMode;

public class PaymentMapper {

    public static PaymentResponse mapToPaymentResponse(TransactionDetails transactionDetails) {
        return new PaymentResponse(
                transactionDetails.getId(),
                transactionDetails.getPaymentStatus(),
                PaymentMode.valueOf(transactionDetails.getPaymentMode()),
                transactionDetails.getAmount(),
                transactionDetails.getPaymentDate(),
                transactionDetails.getOrderId()
        );
    }
}