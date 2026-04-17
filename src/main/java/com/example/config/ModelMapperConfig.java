package com.example.config;

import com.example.dto.CategoryRequestDto;
import com.example.dto.ProductRequestDto;
import com.example.model.Category;
import com.example.model.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper mapper = new ModelMapper();

        // 🔥 CRITICAL: Industry-safe settings
        mapper.getConfiguration()
              .setMatchingStrategy(MatchingStrategies.STRICT)
              .setAmbiguityIgnored(true);

        // ===== CATEGORY =====
        mapper.typeMap(CategoryRequestDto.class, Category.class)
              .addMappings(m -> {
                  m.skip(Category::setId);
                  m.skip(Category::setParentId);
                  m.skip(Category::setLevel);
              });

        // ===== PRODUCT =====
        mapper.typeMap(ProductRequestDto.class, Product.class)
              .addMappings(m -> {
                  m.skip(Product::setId);
              });

        return mapper;
    }
}
