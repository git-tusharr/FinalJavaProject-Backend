package com.example.repository;

import com.example.model.ProductAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductAnswerRepository
        extends JpaRepository<ProductAnswer, Long> {

    Optional<ProductAnswer> findByQuestionId(Long questionId);
}
