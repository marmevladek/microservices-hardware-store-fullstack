package com.hardwarestore.productservice.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductServiceCustomException extends RuntimeException {

    private String errorCode;

    public ProductServiceCustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
