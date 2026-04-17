package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductSearchResponse {
    private Long id;
    private String name;
    private String brand;
    private String category;
    private Double minPrice;
    private String attributes; // example: "8GB, 128GB"
    private String imageUrl;
}
