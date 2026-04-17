package com.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class reeviewRequestDto {
    private Long productId;
    private Long userId;
    private String orderNumber;
    private int rating;
    private String reviewText;
}
