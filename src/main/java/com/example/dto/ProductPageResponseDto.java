package com.example.dto;

import lombok.Data;
import java.util.List;

import com.example.model.ProductAdditionalInfo;
import com.example.model.ProductFeature;
import com.example.model.ProductManufacturerInfo;
import com.example.model.ProductSpecification;
import com.example.model.ProductVideo;
import com.example.model.Review;

@Data
public class ProductPageResponseDto {

    // BASIC INFO
    private Long productId;
    private String name;
    private String description;
    private String brandName;

    // CATEGORY
    private List<CategoryResponseDto> breadcrumb;

    // IMAGES
    private List<ProductImageResponseDto> images;

    // VARIANTS
    private List<VariantResponseDto> variants;

    // 🔥 ADD THIS (CRITICAL FOR VARIANT UI)
    private List<AttributeResponseDto> attributes;

    // SPECIFICATIONS
    private List<ProductSpecification> specifications;

    // ABOUT THIS ITEM
    private List<ProductFeature> features;

    // MANUFACTURER INFO
    private ProductManufacturerInfo manufacturerInfo;

    // Q&A
    private List<QuestionResponseDto> questions;
    
//    additional info
    
 // ADDITIONAL INFO
    private List<ProductAdditionalInfo> additionalInfo;
 
    private List<ProductVideo> videos;


    // REVIEWS
    private List<Review> reviews;
    private RatingSummaryDto ratingSummary;
}
