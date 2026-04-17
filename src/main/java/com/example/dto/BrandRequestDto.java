package com.example.dto;

import lombok.Data;

@Data
public class BrandRequestDto {
    private String name;
    private String slug;
    private String logoUrl;   // Cloudinary URL
}
