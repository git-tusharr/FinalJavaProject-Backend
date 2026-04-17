package com.example.dto;

import lombok.Data;
import java.util.Map;

@Data
public class VariantSelectionRequestDto {

    // attributeId -> attributeValueId
    private Map<Long, Long> attributes;
}
