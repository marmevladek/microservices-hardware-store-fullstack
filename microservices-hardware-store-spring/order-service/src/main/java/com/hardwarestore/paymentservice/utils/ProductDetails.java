package com.hardwarestore.paymentservice.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetails {
    private String productName;
    private Long productId;
    private Long quantity;
    private Long price;
}
