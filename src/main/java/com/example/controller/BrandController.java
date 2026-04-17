package com.example.controller;

import com.example.dto.BrandRequestDto;
import com.example.dto.BrandResponseDto;
import com.example.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    // BULK CREATE
    @PostMapping("/bulk")
    public String createBrands(@RequestBody List<BrandRequestDto> dtos) {
        brandService.createBrandsBulk(dtos);
        return "Brands created successfully";
    }

    // GET ALL BRANDS
    @GetMapping
    public List<BrandResponseDto> getAllBrands() {
        return brandService.getAllBrands();
    }
}
