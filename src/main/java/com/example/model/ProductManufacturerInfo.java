package com.example.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_manufacturer_info")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductManufacturerInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    @Column(columnDefinition = "TEXT")
    private String content;
}
