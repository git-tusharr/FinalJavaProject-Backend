package com.example.repository;

import com.example.model.ProductAdditionalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductAdditionalInfoRepository extends JpaRepository<ProductAdditionalInfo, Long> {
    List<ProductAdditionalInfo> findByProductId(Long productId);
}
