package com.hardwarestore.productservice.repository;

import com.hardwarestore.productservice.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
