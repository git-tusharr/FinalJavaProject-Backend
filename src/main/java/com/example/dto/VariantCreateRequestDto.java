package com.example.dto;

import lombok.Data;
import java.util.Map;

@Data
public class VariantCreateRequestDto {

    private String sku;
    private Double price;
    private Integer stock;
       
    // attributeId -> attributeValueId
    private Map<Long, Long> attributes;
}
