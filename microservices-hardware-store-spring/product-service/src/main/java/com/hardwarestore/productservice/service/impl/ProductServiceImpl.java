package com.hardwarestore.productservice.service.impl;

import com.hardwarestore.productservice.exception.ProductServiceCustomException;
import com.hardwarestore.productservice.mapper.ProductMapper;
import com.hardwarestore.productservice.model.Product;
import com.hardwarestore.productservice.payload.request.ProductRequest;
import com.hardwarestore.productservice.payload.response.ProductResponse;
import com.hardwarestore.productservice.repository.ProductRepository;
import com.hardwarestore.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Long addProduct(ProductRequest productRequest) {
        log.info("ProductServiceImpl | addProduct is called");

        Product product = Product.builder()
                .productName(productRequest.getName())
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .build();

        product = productRepository.save(product);

        log.info("ProductServiceImpl | addProduct | Product Created");
        log.info("ProductServiceImpl | addProduct | Product Id: {}", product.getId());

        return product.getId();
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        log.info("ProductServiceImpl | getProductById is called");
        log.info("ProductServiceImpl | getProductById | Get the product for productId: {}", productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(
                        () -> new ProductServiceCustomException("Product with give Id not found", "PRODUCT_NOT_FOUND")

                );

        ProductResponse productResponse = ProductMapper.mapToProductResponse(product);

        log.info("ProductServiceImpl | getProductById | productResponse: {}", productResponse);

        return productResponse;
    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        log.info("Reduce Quantity {} for Id: {}", quantity, productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(
                        () -> new ProductServiceCustomException("Product with give Id not found", "PRODUCT_NOT_FOUND")
                );

        if (product.getQuantity() < quantity) {
            throw new ProductServiceCustomException("Product does not have sufficient Quantity", "INSUFFICIENT");
        }

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        log.info("Product Quantity updated Successfully");

    }

    @Override
    public void deleteProductById(Long productId) {
        log.info("Product id: {}", productId);

        if (productRepository.existsById(productId)) {
            log.info("Im in this loop {}", !productRepository.existsById(productId));
            throw new ProductServiceCustomException("Product with give Id: " + productId + " not found", "PRODUCT_NOT_FOUND");
        }

        log.info("Deleting Product with id: {}", productId);
        productRepository.deleteById(productId);
    }
}
