package com.example.repository;

import com.example.model.VariantPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VariantPriceRepository
        extends JpaRepository<VariantPrice, Long> {

    Optional<VariantPrice> findByVariantId(Long variantId);
    

}
