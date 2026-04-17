package com.example.repository;

import com.example.model.Product;
import com.example.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findByProductIdAndVariantId(Long productId, Long variantId);

    List<ProductImage> findByProductIdAndVariantIdIsNull(Long productId);

    Optional<ProductImage> findFirstByProductId(Long productId);

}
