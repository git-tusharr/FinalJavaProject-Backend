package com.example.dto;

import lombok.Data;

@Data
public class ProductListItemDto {
    private Long productId;
    private String name;
    private String brand;
    private Double price;
    private String image;
    private Double rating;
}
