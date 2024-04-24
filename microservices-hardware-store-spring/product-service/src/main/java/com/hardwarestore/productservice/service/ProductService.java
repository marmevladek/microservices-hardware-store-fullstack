package com.hardwarestore.productservice.service;

import com.hardwarestore.productservice.payload.request.ProductRequest;
import com.hardwarestore.productservice.payload.response.ProductResponse;

public interface ProductService {

    Long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(Long productId);

    void reduceQuantity(long productId, long quantity);

    void deleteProductById(Long productId);
}
