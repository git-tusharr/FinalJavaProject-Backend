package com.example.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_questions")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    @Column(nullable = false)
    private String question;

    private Boolean isAnswered = false;
}
