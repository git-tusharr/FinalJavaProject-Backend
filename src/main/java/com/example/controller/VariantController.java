package com.example.controller;

import com.example.dto.VariantCreateRequestDto;
import com.example.dto.VariantResponseDto;
import com.example.service.VariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/{productId}/variants")
@RequiredArgsConstructor
public class VariantController {

    private final VariantService service;

    @PostMapping
    public VariantResponseDto createVariant(
            @PathVariable Long productId,
            @RequestBody VariantCreateRequestDto dto
    ) {
        return service.createVariant(productId, dto);
    }

    @GetMapping
    public List<VariantResponseDto> getVariants(
            @PathVariable Long productId
    ) {
        return service.getVariants(productId);
    }
}
