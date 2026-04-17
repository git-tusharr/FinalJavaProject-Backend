package com.example.service;

import com.example.dto.*;
import com.example.model.*;
import com.example.repository.*;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductPageService {

    private final ProductRepository productRepo;
    private final BrandRepository brandRepo;
    private final CategoryService categoryService;
    private final ProductImageService imageService;
    private final VariantService variantService;
    private final ProductSpecificationService specificationService;
    private final ProductFeatureService featureService;
    private final ProductManufacturerInfoService manufacturerService;
    private final ProductQuestionService questionService;
    private final ProductReviewService reviewService;
    private final ReviewService reviewService2;
    private final ProductAttributeService productAttributeService;
    private final ProductAdditionalInfoService additionalInfoService;
    private final ProductVideoService productVideoService;

    
    
    public List<ProductListItemDto> getProductListing() {

        List<Product> products = productRepo.findAll();
        List<ProductListItemDto> list = new ArrayList<>();

        for (Product p : products) {

            ProductListItemDto dto = new ProductListItemDto();

            dto.setProductId(p.getId());
            dto.setName(p.getName());

            // BRAND
            dto.setBrand(
                brandRepo.findById(p.getBrandId())
                         .map(b -> b.getName())
                         .orElse(null)
            );

            // PRICE (lowest variant price)
            dto.setPrice(
                variantService.getLowestPrice(p.getId())
            );

            
            // IMAGE (first product image)
            dto.setImage(
            	    imageService.getImages(p.getId(), null)
            	        .stream()
            	        .findFirst()
            	        .map(ProductImageResponseDto::getImageUrl)
            	        .orElse(null)
            	);


            // RATING
            dto.setRating(
                    reviewService2.getAverageRating(p.getId())
                );

            list.add(dto);
        }

        return list;
    }

    
    
    
    
    public ProductPageResponseDto getProductPage(Long productId) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ProductPageResponseDto res = new ProductPageResponseDto();

        // BASIC INFO
        res.setProductId(product.getId());
        res.setName(product.getName());
        res.setDescription(product.getDescription());

        // BRAND
        res.setBrandName(
                brandRepo.findById(product.getBrandId())
                        .map(b -> b.getName())
                        .orElse(null)
        );

        // CATEGORY BREADCRUMB
        res.setBreadcrumb(
                categoryService.getBreadcrumb(product.getCategoryId())
        );

        // IMAGES
        res.setImages(
                imageService.getImages(productId, null)
        );

        // VARIANTS
        res.setVariants(
                variantService.getVariants(productId)
        );

        // 🔥 ADD THIS (CRITICAL)
        res.setAttributes(
                productAttributeService.getAttributesByProduct(productId)
        );

        // SPECIFICATIONS
        res.setSpecifications(
                specificationService.getSpecs(productId)
        );

        // FEATURES
        res.setFeatures(
                featureService.getFeatures(productId)
        );

        // MANUFACTURER INFO
        res.setManufacturerInfo(
                manufacturerService.get(productId)
        );

        // QUESTIONS
        res.setQuestions(
                questionService.getQnA(productId)
        );

        // REVIEWS
        res.setReviews(
                reviewService2.getReviewsByProduct(productId)
        );

        // RATING SUMMARY
        res.setRatingSummary(
                reviewService2.getRatingSummary(productId)
        );

        
        res.setAdditionalInfo(
                additionalInfoService.getByProduct(productId)
        );
        
        
     // VIDEOS
        res.setVideos(
                productVideoService.getByProduct(productId)
        );

        return res;
    }

}
