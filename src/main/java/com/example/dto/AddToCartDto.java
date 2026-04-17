package com.example.dto;
import lombok.Data;

@Data
public class AddToCartDto {
    private Long userId;
    private Long productId;
    private Long variantId;
    private Integer quantity;
}
