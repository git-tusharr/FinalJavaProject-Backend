package com.example.controller;

import com.example.dto.ProductListItemDto;
import com.example.dto.ProductPageResponseDto;
import com.example.service.ProductPageService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductPageController {

    private final ProductPageService service;

    
    @GetMapping
    public List<ProductListItemDto> listProducts() {
        return service.getProductListing();
    }

    @GetMapping("/{productId}/page")
    public ProductPageResponseDto getProductPage(
            @PathVariable Long productId
    ) {
        return service.getProductPage(productId);
    }
}
