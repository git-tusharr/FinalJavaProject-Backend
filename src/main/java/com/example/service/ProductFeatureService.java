package com.example.service;

import com.example.repository.ProductFeatureRepository;
import com.example.model.ProductFeature;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductFeatureService {

    private final ProductFeatureRepository repo;

    public void saveFeatures(Long productId, List<String> features) {

        repo.deleteByProductId(productId);

        for (String f : features) {
            ProductFeature pf = new ProductFeature();
            pf.setProductId(productId);
            pf.setFeature(f);
            repo.save(pf);
        }
    }

    public List<ProductFeature> getFeatures(Long productId) {
        return repo.findByProductId(productId);
    }
}
