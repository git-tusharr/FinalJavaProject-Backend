package com.example.service;

import com.example.model.Product;
import com.example.model.ProductVideo;
import com.example.repository.ProductRepository;
import com.example.repository.ProductVideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductVideoService {

    private final ProductVideoRepository videoRepo;
    private final ProductRepository productRepo;

    // ⭐ BULK ADD
    public List<ProductVideo> addBulk(Long productId, List<ProductVideo> list) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Invalid Product ID"));

        for (ProductVideo v : list) {
            v.setId(null);
            v.setProduct(product);
        }

        return videoRepo.saveAll(list);
    }

    public List<ProductVideo> getByProduct(Long productId) {
        return videoRepo.findByProductId(productId);
    }
}
