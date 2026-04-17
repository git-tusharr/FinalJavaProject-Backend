package com.example.dto;

import lombok.Data;

@Data
public class ReviewResponseDto {

    private Long id;
    private Integer rating;
    private String review;
    private Long userId;
}
