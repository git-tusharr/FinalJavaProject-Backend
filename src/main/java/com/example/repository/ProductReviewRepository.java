package com.example.repository;

import com.example.model.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductReviewRepository
        extends JpaRepository<ProductReview, Long> {

    List<ProductReview> findByProductId(Long productId);

    @Query("SELECT AVG(r.rating) FROM ProductReview r WHERE r.productId = :productId")
    Double getAverageRating(Long productId);

    Long countByProductId(Long productId);
}
