package com.example.dto;

import lombok.Data;

@Data
public class BrandResponseDto {
    private Long id;
    private String name;
    private String slug;
    private String logoUrl;
}
