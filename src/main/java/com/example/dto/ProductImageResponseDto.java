package com.example.dto;

import lombok.Data;

@Data
public class ProductImageResponseDto {
    private Long id;
    private String imageUrl;
    private Boolean isPrimary;
    private Integer displayOrder;
    private Long variantId;
}
