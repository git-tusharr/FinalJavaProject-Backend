package com.example.controller;

import com.example.dto.ProductRequestDto;
import com.example.dto.ProductResponseDto;
import com.example.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // CREATE PRODUCT
    @PostMapping
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto dto) {
        return productService.createProduct(dto);
    }

    // GET PRODUCT BY SLUG
    @GetMapping("/{slug}")
    public ProductResponseDto getProductBySlug(@PathVariable String slug) {
        return productService.getProductBySlug(slug);
    }
}
