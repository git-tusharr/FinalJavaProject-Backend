package com.example.service;

import com.example.dto.ProductSearchResponse;
import com.example.model.*;
import com.example.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class ProductSearchService {

    private final ProductRepository productRepo;
    private final BrandRepository brandRepo;
    private final CategoryRepository categoryRepo;
    private final VariantRepository variantRepo;
    private final VariantPriceRepository priceRepo;
    private final VariantAttributeValueRepository attrValueRepo;
    private final AttributeValueRepository attributeValueRepo;
    private final ProductImageRepository imageRepo;

    public List<ProductSearchResponse> search(
            String q,
            List<Long> brandIds,
            List<Long> categoryIds,
            Double minPrice,
            Double maxPrice,
            List<Long> attributeValueIds
    ) {

        // 1️⃣ Base search
        List<Product> products =
                (q != null && !q.isBlank())
                        ? productRepo.findByNameContainingIgnoreCase(q)
                        : productRepo.findAll();

        // 2️⃣ Filter by brand
        if (brandIds != null && !brandIds.isEmpty()) {
            products = products.stream()
                    .filter(p -> brandIds.contains(p.getBrandId()))
                    .toList();
        }

        // 3️⃣ Filter by category
        if (categoryIds != null && !categoryIds.isEmpty()) {
            products = products.stream()
                    .filter(p -> categoryIds.contains(p.getCategoryId()))
                    .toList();
        }

        // 4️⃣ Filter by attributes (RAM, Storage, Color, etc)
        if (attributeValueIds != null && !attributeValueIds.isEmpty()) {
            List<Long> matchedVariantIds = attrValueRepo.findByAttributeValueIdIn(attributeValueIds)
                    .stream()
                    .map(VariantAttributeValue::getVariantId)
                    .toList();

            products = products.stream()
                    .filter(p -> variantRepo.findByProductId(p.getId())
                            .stream()
                            .anyMatch(v -> matchedVariantIds.contains(v.getId())))
                    .toList();
        }

        // 5️⃣ Filter by price range
        if (minPrice != null || maxPrice != null) {
            products = products.stream()
                    .filter(p -> {
                        List<Variant> variants = variantRepo.findByProductId(p.getId());
                        return variants.stream().anyMatch(v -> {
                            Optional<VariantPrice> vpList = priceRepo.findByVariantId(v.getId());
                            return vpList.stream().anyMatch(vp -> {
                                if (minPrice != null && vp.getSellingPrice() < minPrice) return false;
                                if (maxPrice != null && vp.getSellingPrice() > maxPrice) return false;
                                return true;
                            });
                        });
                    }).toList();
        }

        // 6️⃣ Build result DTO
        return products.stream().map(p -> {
            String brand = brandRepo.findById(p.getBrandId())
                    .map(Brand::getName)
                    .orElse(null);

            String category = categoryRepo.findById(p.getCategoryId())
                    .map(Category::getName)
                    .orElse(null);

            List<Variant> variants = variantRepo.findByProductId(p.getId());

            List<String> attrNames = new ArrayList<>();
            for (Variant v : variants) {
                List<VariantAttributeValue> vals = attrValueRepo.findByVariantId(v.getId());
                for (VariantAttributeValue val : vals) {
                    attributeValueRepo.findById(val.getAttributeValueId())
                            .ifPresent(a -> attrNames.add(a.getValue()));
                }
            }

            Double min = variants.stream()
                    .flatMap(v -> priceRepo.findByVariantId(v.getId()).stream())
                    .map(VariantPrice::getSellingPrice)
                    .min(Double::compare)
                    .orElse(0.0);

            String imgUrl = imageRepo.findFirstByProductId(p.getId())
                    .map(ProductImage::getImageUrl)
                    .orElse(null);

            return new ProductSearchResponse(
                    p.getId(),
                    p.getName(),
                    brand,
                    category,
                    min,
                    String.join(", ", attrNames),
                    imgUrl
            );
        }).toList();
    }
}
