package com.example.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class WishlistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long productId;
}
