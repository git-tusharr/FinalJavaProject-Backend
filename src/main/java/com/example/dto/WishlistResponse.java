package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WishlistResponse {
    private Long id;
    private Long productId;
    private String name;
    private String imageUrl;
}
