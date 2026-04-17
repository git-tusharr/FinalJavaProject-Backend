package com.example.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class VariantResponseDto {

    private Long id;
    private String sku;
    private Double price;
    private Integer stock;

    // attributeId -> attributeValueId
    private Map<Long, Long> attributes;
    
    private List<ProductImageResponseDto> images;
}
