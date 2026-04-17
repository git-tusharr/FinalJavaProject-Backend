package com.example.service;

import com.example.dto.ProductRequestDto;
import com.example.dto.ProductResponseDto;
import com.example.model.Product;
import com.example.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    // CREATE PRODUCT (BASE CONTAINER)
    public ProductResponseDto createProduct(ProductRequestDto dto) {

        Product product = modelMapper.map(dto, Product.class);
        product.setIsActive(true);

        Product savedProduct = productRepository.save(product);

        return modelMapper.map(savedProduct, ProductResponseDto.class);
    }

    // GET PRODUCT BY SLUG (PRODUCT PAGE ENTRY POINT)
    public ProductResponseDto getProductBySlug(String slug) {

        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return modelMapper.map(product, ProductResponseDto.class);
    }
}
