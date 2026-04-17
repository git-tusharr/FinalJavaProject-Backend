package com.example.controller;

import com.example.dto.ProductAttributeRequestDto;
import com.example.service.ProductAttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products/{productId}/attributes")
@RequiredArgsConstructor
public class ProductAttributeController {

    private final ProductAttributeService service;

    @PostMapping
    public String assignAttributes(
            @PathVariable Long productId,
            @RequestBody ProductAttributeRequestDto dto
    ) {
        service.assignAttributesToProduct(productId, dto);
        return "Attributes assigned to product";
    }
    
    
    

    // ✅ GET ATTRIBUTES OF PRODUCT (NEW)
    @GetMapping
    public Object getProductAttributes(
            @PathVariable Long productId
    ) {
        return service.getAttributesByProduct(productId);
    }
}
