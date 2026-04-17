package com.example.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_answers")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long questionId;

    @Column(nullable = false)
    private String answer;
}
