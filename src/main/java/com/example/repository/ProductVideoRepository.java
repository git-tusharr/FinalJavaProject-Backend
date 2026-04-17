package com.example.repository;

import com.example.model.ProductVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductVideoRepository extends JpaRepository<ProductVideo, Long> {
    List<ProductVideo> findByProductId(Long productId);
}
