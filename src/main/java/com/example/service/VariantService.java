package com.example.service;

import com.example.dto.VariantCreateRequestDto;
import com.example.dto.VariantResponseDto;
import com.example.model.Variant;
import com.example.model.VariantAttributeValue;
import com.example.repository.VariantAttributeValueRepository;
import com.example.repository.VariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class VariantService {

    private final VariantRepository variantRepo;
    private final VariantAttributeValueRepository vavRepo;
    private final ProductImageService imageService;

    // CREATE VARIANT
    public VariantResponseDto createVariant(Long productId, VariantCreateRequestDto dto) {

        Variant variant = new Variant();
        variant.setProductId(productId);
        variant.setSku(dto.getSku());
        variant.setPrice(dto.getPrice());
        variant.setStock(dto.getStock());

        Variant savedVariant = variantRepo.save(variant);

        for (Map.Entry<Long, Long> entry : dto.getAttributes().entrySet()) {

            VariantAttributeValue vav = new VariantAttributeValue();
            vav.setVariantId(savedVariant.getId()); // ✅ IMPORTANT
            vav.setAttributeId(entry.getKey());
            vav.setAttributeValueId(entry.getValue());

            vavRepo.save(vav);
        }

        // ✅ RETURN ID TO FRONTEND
        VariantResponseDto res = new VariantResponseDto();
        res.setId(savedVariant.getId());
        res.setSku(savedVariant.getSku());
        res.setPrice(savedVariant.getPrice());
        res.setStock(savedVariant.getStock());
        res.setAttributes(dto.getAttributes());

        return res;
    }


    // GET VARIANTS OF PRODUCT
    public List<VariantResponseDto> getVariants(Long productId) {

        List<Variant> variants = variantRepo.findByProductId(productId);
        List<VariantResponseDto> response = new ArrayList<>();

        for (Variant v : variants) {

            VariantResponseDto dto = new VariantResponseDto();
            dto.setId(v.getId());
            dto.setSku(v.getSku());
            dto.setPrice(v.getPrice());
            dto.setStock(v.getStock());
            dto.setImages(
                    imageService.getImages(productId, v.getId())
            );

            Map<Long, Long> attrs = new HashMap<>();
            for (VariantAttributeValue vav : vavRepo.findByVariantId(v.getId())) {
                attrs.put(vav.getAttributeId(), vav.getAttributeValueId());
            }

            dto.setAttributes(attrs);
            response.add(dto);
        }

        return response;
    }
    
    
    public Double getLowestPrice(Long productId) {
        return variantRepo.findByProductId(productId)
                .stream()
                .map(Variant::getPrice)
                .min(Double::compareTo)
                .orElse(null);
    }

}
