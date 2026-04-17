package com.example.dto;

import lombok.Data;

@Data
public class VariantPricingResponseDto {

    private Double mrp;
    private Double sellingPrice;
    private Double discount;
    private Double finalPrice;
}
