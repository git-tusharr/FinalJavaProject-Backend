package com.example.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_specifications")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductSpecification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private String specKey;
    private String specValue;
}
