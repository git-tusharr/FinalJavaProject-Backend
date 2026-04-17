package com.example.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "variant_discounts")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class VariantDiscount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long variantId;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    private Double discountValue;

    private Boolean isActive = true;
}

