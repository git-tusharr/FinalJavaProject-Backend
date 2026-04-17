package com.example.service;

import com.example.model.ProductManufacturerInfo;
import com.example.repository.ProductManufacturerInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductManufacturerInfoService {

    private final ProductManufacturerInfoRepository repo;

    public void save(Long productId, String content) {

        ProductManufacturerInfo info =
                repo.findByProductId(productId)
                .orElse(new ProductManufacturerInfo());

        info.setProductId(productId);
        info.setContent(content);

        repo.save(info);
    }

    public ProductManufacturerInfo get(Long productId) {
        return repo.findByProductId(productId).orElse(null);
    }
}
