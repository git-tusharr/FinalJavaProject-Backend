package com.example.controller;

import com.example.dto.ProductSearchResponse;
import com.example.model.Brand;
import com.example.model.Category;
import com.example.model.AttributeValue;
import com.example.repository.BrandRepository;
import com.example.repository.CategoryRepository;
import com.example.repository.AttributeValueRepository;
import com.example.service.ProductSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/search")
@RequiredArgsConstructor
public class ProductSearchController {

    private final ProductSearchService service;
    private final BrandRepository brandRepo;
    private final CategoryRepository categoryRepo;
    private final AttributeValueRepository attributeValueRepo;

    @GetMapping
    public List<ProductSearchResponse> search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) List<Long> brandId,
            @RequestParam(required = false) List<Long> categoryId,
            @RequestParam(required = false) List<Long> attributeValueId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        return service.search(q, brandId, categoryId, minPrice, maxPrice, attributeValueId);
    }

    @GetMapping("/brands")
    public List<Brand> brands() {
        return brandRepo.findAll();
    }

    @GetMapping("/categories")
    public List<Category> categories() {
        return categoryRepo.findAll();
    }

    @GetMapping("/attributes")
    public List<AttributeValue> attributes() {
        return attributeValueRepo.findAll();
    }
}
