package com.example.repository;

import com.example.model.ProductQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductQuestionRepository
        extends JpaRepository<ProductQuestion, Long> {

    List<ProductQuestion> findByProductId(Long productId);
}
