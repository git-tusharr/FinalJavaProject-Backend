package com.example.dto;

import lombok.Data;
import java.util.List;

@Data
public class AttributeRequestDto {
    private String name;
    private List<String> values;
}
