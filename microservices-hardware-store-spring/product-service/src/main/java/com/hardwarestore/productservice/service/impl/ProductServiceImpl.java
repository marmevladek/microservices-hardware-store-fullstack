package com.hardwarestore.productservice.service.impl;

import com.hardwarestore.productservice.exception.ProductServiceCustomException;
import com.hardwarestore.productservice.mapper.ProductMapper;
import com.hardwarestore.productservice.model.Image;
import com.hardwarestore.productservice.model.Product;
import com.hardwarestore.productservice.payload.request.ProductRequest;
import com.hardwarestore.productservice.payload.response.ProductResponse;
import com.hardwarestore.productservice.repository.ImageRepository;
import com.hardwarestore.productservice.repository.ProductRepository;
import com.hardwarestore.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;

    private final Path root = Paths.get("/Users/vladislavtezev/projects/microservices-hardware-store-fullstack/microservices-hardware-store-react/public");

    @Override
    public Long addProduct(ProductRequest productRequest, MultipartFile file) {
        log.info("ProductServiceImpl | addProduct is called");



        Product product = Product.builder()
                .productName(productRequest.getName())
                .quantity(productRequest.getQuantity())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();


        Product savedProduct = productRepository.save(product);

        saveImage(file, savedProduct);

        log.info("ProductServiceImpl | addProduct | Product Created");
        log.info("ProductServiceImpl | addProduct | Product Id: {}", savedProduct.getId());

        return savedProduct.getId();
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
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductMapper::mapToProductResponse)
                .collect(Collectors.toList());
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

        if (!productRepository.existsById(productId)) {
            log.info("Im in this loop {}", !productRepository.existsById(productId));
            throw new ProductServiceCustomException("Product with give Id: " + productId + " not found", "PRODUCT_NOT_FOUND");
        }

        log.info("Deleting Product with id: {}", productId);
        productRepository.deleteById(productId);
    }

    @Override
    public void initStorage(Path root) {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public void saveImage(MultipartFile file, Product product) {
        List<Image> images = new ArrayList<>();
        try {


            Path newRoot = Path.of(this.root + "/images/" + product.getId());
            initStorage(newRoot);
            Files.copy(file.getInputStream(), newRoot.resolve(Objects.requireNonNull(file.getOriginalFilename())));

            String url = "/images/" + product.getId();
            Image image = new Image(
                    file.getOriginalFilename(),
                    url
            );

            imageRepository.save(image);

            images.add(image);

            product.setImages(images);
            productRepository.save(product);

        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new ProductServiceCustomException("File already exists", "FILE_ALREADY_EXISTS");
            }

            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Resource loadImage(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files
                    .walk(this.root, 1)
                    .filter(path -> !path.equals(this.root))
                    .map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }


}
