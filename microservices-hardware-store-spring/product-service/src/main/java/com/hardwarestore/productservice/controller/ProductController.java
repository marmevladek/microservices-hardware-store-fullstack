package com.hardwarestore.productservice.controller;

import com.hardwarestore.productservice.payload.request.ProductRequest;
import com.hardwarestore.productservice.payload.response.ProductResponse;
import com.hardwarestore.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Long> addProduct(@RequestParam("name") String name,
                                           @RequestParam("price") long price,
                                           @RequestParam("description") String description,
                                           @RequestParam("quantity") long quantity,
                                           @RequestParam("file") MultipartFile file) {

        log.info("ProductController | addProduct is called");
        ProductRequest productRequest = new ProductRequest(
                name,
                price,
                description,
                quantity
        );


        log.info("ProductController | addProduct | productRequest: {}", productRequest.toString());

        long productId = productService.addProduct(productRequest, file);
        return new ResponseEntity<>(productId, HttpStatus.CREATED); //productid
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long productId) {

        log.info("ProductController | getProductById is called");

        log.info("ProductController | getProductById | productId : {}", productId);

        ProductResponse productResponse = productService.getProductById(productId);

        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> productResponses = productService.getAllProducts();
        System.out.println(productResponses.toString());
        return new ResponseEntity<>(productResponses, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @PutMapping("/reduceQuantity/{id}")
    public ResponseEntity<Void> reduceQuantity(@PathVariable("id") long productId, @RequestParam long quantity) {
        log.info("ProductController | reduceQuantity | productId : {}", productId);
        log.info("ProductController | reduceQuantity | quantity : {}", quantity);

        productService.reduceQuantity(productId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") long productId) {
        productService.deleteProductById(productId);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/uploadImage")
    public void uploadImage(@RequestParam("file") MultipartFile file) {
        System.out.println(file.getOriginalFilename());
    }
}
