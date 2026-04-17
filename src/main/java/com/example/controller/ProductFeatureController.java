package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.ProductFeature;
import com.example.service.ProductFeatureService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products/{productId}/features")
@RequiredArgsConstructor
public class ProductFeatureController {

    private final ProductFeatureService service;

    @PostMapping("/bulk")
    public String saveFeatures(
            @PathVariable Long productId,
            @RequestBody List<String> features
    ) {
        service.saveFeatures(productId, features);
        return "Features saved";
    }

    @GetMapping
    public List<ProductFeature> getFeatures(
            @PathVariable Long productId
    ) {
        return service.getFeatures(productId);
    }
}
