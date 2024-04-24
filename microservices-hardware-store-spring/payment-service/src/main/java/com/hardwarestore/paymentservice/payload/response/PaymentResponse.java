package com.hardwarestore.paymentservice.payload.response;

import com.hardwarestore.paymentservice.utils.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
public class PaymentResponse {

    private long paymentId;
    private String status;
    private PaymentMode paymentMode;
    private long amount;
    private Instant paymentDate;
    private long orderId;

    public PaymentResponse(Long id, String paymentStatus, PaymentMode paymentMode, Long amount, Instant paymentDate, Long orderId) {
        this.paymentId = id;
        this.status = paymentStatus;
        this.paymentMode = paymentMode;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.orderId = orderId;
    }
}
