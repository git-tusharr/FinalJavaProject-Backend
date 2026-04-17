package com.example.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProductAttributeRequestDto {
    private List<Long> attributeIds;
}
