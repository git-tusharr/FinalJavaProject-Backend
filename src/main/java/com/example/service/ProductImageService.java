package com.example.service;

import com.cloudinary.Cloudinary;
import com.example.dto.ProductImageResponseDto;
import com.example.model.ProductImage;
import com.example.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductImageRepository repository;
    private final Cloudinary cloudinary;
    private final ModelMapper modelMapper;

    // BULK UPLOAD IMAGES
    public void uploadImages(
            Long productId,
            Long variantId,
            List<MultipartFile> files
    ) throws Exception {

        int order = 1;

        for (MultipartFile file : files) {

            Map<?, ?> uploadResult = cloudinary.uploader()
                    .upload(file.getBytes(), Map.of());

            ProductImage image = new ProductImage();
            image.setProductId(productId);
            image.setVariantId(variantId);
            image.setImageUrl(uploadResult.get("secure_url").toString());
            image.setDisplayOrder(order++);
            image.setIsPrimary(false);

            repository.save(image);
        }
    }

    // SET PRIMARY IMAGE
    public void setPrimaryImage(Long imageId) {

        ProductImage image = repository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        // Reset others
        List<ProductImage> images = image.getVariantId() == null
                ? repository.findByProductIdAndVariantIdIsNull(image.getProductId())
                : repository.findByProductIdAndVariantId(image.getProductId(), image.getVariantId());

        images.forEach(img -> img.setIsPrimary(false));
        repository.saveAll(images);

        image.setIsPrimary(true);
        repository.save(image);
    }

    // GET IMAGES FOR PRODUCT / VARIANT
    public List<ProductImageResponseDto> getImages(Long productId, Long variantId) {

        List<ProductImage> images = variantId == null
                ? repository.findByProductIdAndVariantIdIsNull(productId)
                : repository.findByProductIdAndVariantId(productId, variantId);

        return images.stream()
                .map(img -> modelMapper.map(img, ProductImageResponseDto.class))
                .toList();
    }
}
