package com.example.service;

import com.example.dto.ProductSpecificationDto;
import com.example.model.ProductSpecification;
import com.example.repository.ProductSpecificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSpecificationService {

    private final ProductSpecificationRepository repo;

    public void saveBulk(Long productId, List<ProductSpecificationDto> dtos) {

        repo.deleteByProductId(productId);

        for (ProductSpecificationDto dto : dtos) {
            ProductSpecification spec = new ProductSpecification();
            spec.setProductId(productId);
            spec.setSpecKey(dto.getSpecKey());
            spec.setSpecValue(dto.getSpecValue());
            repo.save(spec);
        }
    }

    public List<ProductSpecification> getSpecs(Long productId) {
        return repo.findByProductId(productId);
    }
}
