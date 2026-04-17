package com.example.service;

import com.example.dto.CategoryRequestDto;
import com.example.dto.CategoryResponseDto;
import com.example.model.Category;
import com.example.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public void createCategoriesBulk(List<CategoryRequestDto> dtos) {

        Map<String, Category> tempMap = new HashMap<>();

        // 1️⃣ Save ROOT categories
        for (CategoryRequestDto dto : dtos) {
            if (dto.getParentTempId() == null) {

            	  Category category = modelMapper.map(dto, Category.class);
                category.setLevel(1);

                categoryRepository.save(category);
                tempMap.put(dto.getTempId(), category);
            }
        }

        // 2️⃣ Save SUB categories
        boolean pending;
        do {
            pending = false;

            for (CategoryRequestDto dto : dtos) {

            	  if (dto.getParentTempId() != null
                          && !tempMap.containsKey(dto.getTempId())) {

                    Category parent = tempMap.get(dto.getParentTempId());

                    if (parent != null) {
                        Category category = modelMapper.map(dto, Category.class);
                        category.setParentId(parent.getId());
                        category.setLevel(parent.getLevel() + 1);

                        categoryRepository.save(category);
                        tempMap.put(dto.getTempId(), category);
                    } else {
                        pending = true;
                    }
                }
            }
        } while (pending);
    }

    // Breadcrumb stays SAME
    public List<CategoryResponseDto> getBreadcrumb(Long categoryId) {

        List<CategoryResponseDto> breadcrumb = new ArrayList<>();
        Category current = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        while (current != null) {
            breadcrumb.add(modelMapper.map(current, CategoryResponseDto.class));
            current = current.getParentId() == null
                    ? null
                    : categoryRepository.findById(current.getParentId()).orElse(null);
        }

        Collections.reverse(breadcrumb);
        return breadcrumb;
    }
}





