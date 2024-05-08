package com.hardwarestore.productservice.payload.response;

import com.hardwarestore.productservice.model.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private long productId;
    private String productName;
    private long price;
    private String description;
    private long quantity;
    private List<Image> images;
}
