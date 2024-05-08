package com.hardwarestore.productservice.service;

import com.hardwarestore.productservice.model.Product;
import com.hardwarestore.productservice.payload.request.ProductRequest;
import com.hardwarestore.productservice.payload.response.ProductResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface ProductService {

    Long addProduct(ProductRequest productRequest, MultipartFile file);

    ProductResponse getProductById(Long productId);

    List<ProductResponse> getAllProducts();

    void reduceQuantity(long productId, long quantity);

    void deleteProductById(Long productId);

    void initStorage(Path root);

    void saveImage(MultipartFile file, Product product);

    Resource loadImage(String filename);

    Stream<Path> loadAll();
}
