package com.example.dto;

import com.example.model.DiscountType;
import lombok.Data;

@Data
public class VariantDiscountRequestDto {
    private DiscountType discountType;
    private Double discountValue;
}
