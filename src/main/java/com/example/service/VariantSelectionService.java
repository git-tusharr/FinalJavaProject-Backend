package com.example.service;

import com.example.dto.VariantSelectionRequestDto;
import com.example.dto.VariantSelectionResponseDto;
import com.example.model.Variant;
import com.example.model.VariantAttributeValue;
import com.example.repository.VariantAttributeValueRepository;
import com.example.repository.VariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VariantSelectionService {

    private final VariantRepository variantRepo;
    private final VariantAttributeValueRepository vavRepo;

    public VariantSelectionResponseDto selectVariant(
            Long productId,
            VariantSelectionRequestDto request
    ) {

        List<Variant> variants = variantRepo.findByProductId(productId);

        for (Variant variant : variants) {

            List<VariantAttributeValue> attrs =
                    vavRepo.findByVariantId(variant.getId());

            if (matches(attrs, request.getAttributes())) {

                VariantSelectionResponseDto res =
                        new VariantSelectionResponseDto();

                res.setVariantId(variant.getId());
                res.setPrice(variant.getPrice());
                res.setStock(variant.getStock());

                return res;
            }
        }

        throw new RuntimeException("No matching variant found");
    }

    private boolean matches(
            List<VariantAttributeValue> variantAttrs,
            Map<Long, Long> selectedAttrs
    ) {

        if (variantAttrs.size() != selectedAttrs.size()) return false;

        for (VariantAttributeValue vav : variantAttrs) {
            Long selectedValue =
                    selectedAttrs.get(vav.getAttributeId());

            if (selectedValue == null ||
                !selectedValue.equals(vav.getAttributeValueId())) {
                return false;
            }
        }

        return true;
    }
}
