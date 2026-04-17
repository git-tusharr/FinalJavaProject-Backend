package com.example.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductResponseDto {

    private Long id;
    private String name;
    private String slug;
    private String description;

    private Long categoryId;
    private Long brandId;

    private Boolean isActive;
    private LocalDateTime createdAt;
}
