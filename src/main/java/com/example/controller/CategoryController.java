package com.example.controller;

import com.example.dto.CategoryRequestDto;
import com.example.dto.CategoryResponseDto;
import com.example.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/bulk")
    public String createCategories(@RequestBody List<CategoryRequestDto> dtos) {
        categoryService.createCategoriesBulk(dtos);
        return "Categories created successfully";
    }

    @GetMapping("/breadcrumb/{categoryId}")
    public List<CategoryResponseDto> getBreadcrumb(@PathVariable Long categoryId) {
        return categoryService.getBreadcrumb(categoryId);
    }
}
