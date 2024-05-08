package com.hardwarestore.productservice.payload.request;

import com.hardwarestore.productservice.model.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ProductRequest {
    private String name;
    private long price;
    private String description;
    private long quantity;
}
