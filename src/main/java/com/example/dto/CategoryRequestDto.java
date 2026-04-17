package com.example.dto;

import lombok.Data;

@Data
public class CategoryRequestDto {
    private String tempId;          // frontend temp id
    private String name;
    private String slug;
    private String parentTempId;    // parent reference
}
