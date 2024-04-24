package com.hardwarestore.paymentservice.payload.request;

import com.hardwarestore.paymentservice.utils.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {

    private Long orderId;
    private Long amount;
    private String referenceNumber;
    private PaymentMode paymentMode;
}
