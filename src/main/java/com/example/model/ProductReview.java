package com.example.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_reviews")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Long userId;     // reviewer (from auth)

    private Integer rating;  // 1 to 5

    @Column(length = 2000)
    private String review;
}
