package com.example.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "variant_attribute_values")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class VariantAttributeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long variantId;
    private Long attributeId;
    private Long attributeValueId;
}
