package com.example.repository;

import com.example.model.ProductFeature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductFeatureRepository
        extends JpaRepository<ProductFeature, Long> {

    List<ProductFeature> findByProductId(Long productId);
    void deleteByProductId(Long productId);
}
