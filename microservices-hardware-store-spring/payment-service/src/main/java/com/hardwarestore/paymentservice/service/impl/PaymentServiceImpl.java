package com.hardwarestore.paymentservice.service.impl;

import com.hardwarestore.paymentservice.exception.PaymentServiceCustomException;
import com.hardwarestore.paymentservice.mapper.PaymentMapper;
import com.hardwarestore.paymentservice.model.TransactionDetails;
import com.hardwarestore.paymentservice.payload.request.PaymentRequest;
import com.hardwarestore.paymentservice.payload.response.PaymentResponse;
import com.hardwarestore.paymentservice.repository.TransactionDetailsRepository;
import com.hardwarestore.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Log4j2
public class PaymentServiceImpl implements PaymentService {

    private final TransactionDetailsRepository transactionDetailsRepository;


    @Override
    public Long doPayment(PaymentRequest paymentRequest) {
        log.info("PaymentServiceIMpl | doPayment is called");
        log.info("PaymentServiceImpl | doPayment | Recording Payment Details: {}", paymentRequest);

        TransactionDetails transactionDetails = TransactionDetails.builder()
                .paymentDate(Instant.now())
                .paymentMode(paymentRequest.getPaymentMode().name())
                .paymentStatus("SUCCESS")
                .orderId(paymentRequest.getOrderId())
                .referenceNumber(paymentRequest.getReferenceNumber())
                .amount(paymentRequest.getAmount())
                .build();

        transactionDetails = transactionDetailsRepository.save(transactionDetails);

        log.info("Transaction Completed with Id: {}", transactionDetails.getId());

        return transactionDetails.getId();
    }

    @Override
    public PaymentResponse getPaymentDetailsByOrderId(Long orderId) {
        log.info("PaymentServiceIMpl | GetPaymentDetailsByOrderId is called");
        log.info("PaymentServiceImpl | GetPaymentDetailsByOrderId | Getting payment details for the Order Id: {}", orderId);

        TransactionDetails transactionDetails = transactionDetailsRepository.findByOrderId(orderId)
                .orElseThrow(
                        () -> new PaymentServiceCustomException("Transaction Details with given id not found", "TRANSACTION_NOT_FOUND")
                );

        PaymentResponse paymentResponse = PaymentMapper.mapToPaymentResponse(transactionDetails);

        log.info("PaymentServiceImpl | getPaymentDetailsByOrderId | paymentResponse: {}", paymentResponse);

        return paymentResponse;
    }
}
