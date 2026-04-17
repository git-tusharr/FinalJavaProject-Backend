package com.example.repository;

import com.example.model.ProductManufacturerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductManufacturerInfoRepository
        extends JpaRepository<ProductManufacturerInfo, Long> {

    Optional<ProductManufacturerInfo> findByProductId(Long productId);
}
