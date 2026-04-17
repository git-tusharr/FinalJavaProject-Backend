package com.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String slug;

    @Column(length = 2000)
    private String description;

    private Long categoryId;   // FK → Category
    private Long brandId;      // FK → Brand

    private Boolean isActive = true;

    private LocalDateTime createdAt = LocalDateTime.now();
}
