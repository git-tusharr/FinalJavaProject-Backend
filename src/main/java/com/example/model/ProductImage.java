package com.example.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_images")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;      // FK → Product (required)
    private Long variantId;      // FK → Variant (nullable)

    private String imageUrl;     // Cloudinary URL

    private Boolean isPrimary = false;
    private Integer displayOrder;
}

