package com.hardwarestore.productservice.mapper;

import com.hardwarestore.productservice.model.Product;
import com.hardwarestore.productservice.payload.response.ProductResponse;

public class ProductMapper {

    public static ProductResponse mapToProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getProductName(),
                product.getPrice(),
                product.getDescription(),
                product.getQuantity(),
                product.getImages()
        );
    }
}
