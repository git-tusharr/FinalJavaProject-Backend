package com.example.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "attributes")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
