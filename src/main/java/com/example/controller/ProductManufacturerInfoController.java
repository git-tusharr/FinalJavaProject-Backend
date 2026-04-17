package com.example.controller;

import com.example.model.ProductManufacturerInfo;
import com.example.service.ProductManufacturerInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products/{productId}/manufacturer")
@RequiredArgsConstructor
public class ProductManufacturerInfoController {

    private final ProductManufacturerInfoService service;

    /**
     * Save or update "From the Manufacturer" content
     */
    @PostMapping
    public String saveManufacturerInfo(
            @PathVariable Long productId,
            @RequestBody String content
    ) {
        service.save(productId, content);
        return "Manufacturer info saved successfully";
    }

    /**
     * Get "From the Manufacturer" content
     */
    @GetMapping
    public ProductManufacturerInfo getManufacturerInfo(
            @PathVariable Long productId
    ) {
        return service.get(productId);
    }
}
