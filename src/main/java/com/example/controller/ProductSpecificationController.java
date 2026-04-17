package com.example.controller;

import com.example.dto.ProductSpecificationDto;
import com.example.model.ProductSpecification;
import com.example.service.ProductSpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/{productId}/specifications")
@RequiredArgsConstructor
public class ProductSpecificationController {

    private final ProductSpecificationService service;

    @PostMapping("/bulk")
    public String saveSpecs(
            @PathVariable Long productId,
            @RequestBody List<ProductSpecificationDto> dtos
    ) {
        service.saveBulk(productId, dtos);
        return "Specifications saved";
    }

    @GetMapping
    public List<ProductSpecification> getSpecs(
            @PathVariable Long productId
    ) {
        return service.getSpecs(productId);
    }
}
