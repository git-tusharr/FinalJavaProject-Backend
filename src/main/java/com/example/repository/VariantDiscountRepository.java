package com.example.repository;

import com.example.model.VariantDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VariantDiscountRepository
        extends JpaRepository<VariantDiscount, Long> {

    Optional<VariantDiscount> findByVariantIdAndIsActiveTrue(Long variantId);
}
