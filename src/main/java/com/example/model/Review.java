package com.example.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private Long userId;
    private String orderNumber;

    private int rating;

    @Column(length = 1000)
    private String reviewText;

    private LocalDateTime createdAt = LocalDateTime.now();
}
