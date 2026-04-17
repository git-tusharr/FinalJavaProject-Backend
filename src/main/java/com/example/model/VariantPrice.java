package com.example.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "variant_prices")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class VariantPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long variantId;

    private Double mrp;
    private Double sellingPrice;
}
