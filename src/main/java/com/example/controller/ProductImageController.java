package com.example.controller;

import com.example.dto.ProductImageResponseDto;
import com.example.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products/{productId}/images")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService service;

    // BULK UPLOAD
    @PostMapping
    public String uploadImages(
            @PathVariable Long productId,
            @RequestParam(required = false) Long variantId,
            @RequestParam("files") List<MultipartFile> files
    ) throws Exception {

        service.uploadImages(productId, variantId, files);
        
        return "Images uploaded successfully";
    }

    // SET PRIMARY IMAGE
    @PutMapping("/{imageId}/set-primary")
    public String setPrimary(@PathVariable Long imageId) {
        service.setPrimaryImage(imageId);
        return "Primary image set";
    }

    // GET IMAGES
    @GetMapping
    public List<ProductImageResponseDto> getImages(
            @PathVariable Long productId,
            @RequestParam(required = false) Long variantId
    ) {
        return service.getImages(productId, variantId);
    }
}
