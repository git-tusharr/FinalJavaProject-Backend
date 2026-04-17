package com.example.controller;

import com.example.dto.VariantSelectionRequestDto;
import com.example.dto.VariantSelectionResponseDto;
import com.example.service.VariantSelectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products/{productId}/variants")
@RequiredArgsConstructor
public class VariantSelectionController {

    private final VariantSelectionService service;

    @PostMapping("/select")
    public VariantSelectionResponseDto selectVariant(
            @PathVariable Long productId,
            @RequestBody VariantSelectionRequestDto dto
    ) {
        return service.selectVariant(productId, dto);
    }
}
