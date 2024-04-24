package com.hardwarestore.productservice.mapper;

import com.hardwarestore.productservice.model.Product;
import com.hardwarestore.productservice.payload.response.ProductResponse;

public class ProductMapper {

    public static ProductResponse mapToProductResponse(Product product) {
        return new ProductResponse(
                product.getProductName(),
                product.getId(),
                product.getPrice(),
                product.getQuantity()
        );
    }
}
