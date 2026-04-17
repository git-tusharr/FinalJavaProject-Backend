package com.example.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "attribute_values")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AttributeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long attributeId;

    private String value;
}
