package com.example.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_features")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private String feature;
}
