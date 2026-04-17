package com.example.controller;

import com.example.dto.VariantDiscountRequestDto;
import com.example.dto.VariantPricingResponseDto;
import com.example.service.VariantPricingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/variants/{variantId}/pricing")
@RequiredArgsConstructor
public class VariantPricingController {

    private final VariantPricingService service;

    /**
     * Apply or update a discount for a variant
     */
    @PostMapping("/discount")
    public String setDiscount(
            @PathVariable Long variantId,
            @RequestBody VariantDiscountRequestDto dto
    ) {
        service.setDiscount(variantId, dto);
        return "Discount applied";
    }

    /**
     * Get the final pricing after discount
     */
    @GetMapping
    public VariantPricingResponseDto getPricing(
            @PathVariable Long variantId
    ) {
        return service.getPricing(variantId);
    }
}
