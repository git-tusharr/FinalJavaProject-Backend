package com.example.service;

import com.example.model.Product;
import com.example.model.ProductAdditionalInfo;
import com.example.repository.ProductAdditionalInfoRepository;
import com.example.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAdditionalInfoService {

    private final ProductAdditionalInfoRepository infoRepo;
    private final ProductRepository productRepo;

    // ⭐ BULK ADD
    public List<ProductAdditionalInfo> addBulk(Long productId, List<ProductAdditionalInfo> list) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Invalid Product ID"));

        for (ProductAdditionalInfo info : list) {
            info.setId(null);         // mark as new record
            info.setProduct(product); // attach FK
        }

        return infoRepo.saveAll(list);
    }

    public List<ProductAdditionalInfo> getByProduct(Long productId) {
        return infoRepo.findByProductId(productId);
    }
}
