package com.example.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "variants")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Variant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private String sku;  

    private Double price;

    private Integer stock;

    private Boolean isActive = true;
}
