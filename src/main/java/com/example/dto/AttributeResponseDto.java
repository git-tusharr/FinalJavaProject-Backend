package com.example.dto;

import lombok.Data;
import java.util.List;

import com.example.model.AttributeValue;

@Data
public class AttributeResponseDto {
    private Long id;
    private String name;
    private List<AttributeValue> values;
}
