package com.example.service;

import com.example.dto.VariantDiscountRequestDto;
import com.example.dto.VariantPricingResponseDto;
import com.example.model.DiscountType;
import com.example.model.Variant;
import com.example.model.VariantDiscount;
import com.example.repository.VariantDiscountRepository;
import com.example.repository.VariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VariantPricingService {

    private final VariantRepository variantRepo;
    private final VariantDiscountRepository discountRepo;

    /**
     * Create/update discount for variant
     */
    public void setDiscount(Long variantId, VariantDiscountRequestDto dto) {

        VariantDiscount discount = discountRepo
                .findByVariantIdAndIsActiveTrue(variantId)
                .orElse(new VariantDiscount());

        discount.setVariantId(variantId);
        discount.setDiscountType(dto.getDiscountType());
        discount.setDiscountValue(dto.getDiscountValue());
        discount.setIsActive(true);

        discountRepo.save(discount);
    }

    /**
     * Compute price using only Variant.price + discount if exists
     */
    public VariantPricingResponseDto getPricing(Long variantId) {

        Variant variant = variantRepo.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Variant not found"));

        double mrp = variant.getPrice();
        double discountAmount = 0.0;
        double finalPrice = mrp;

        var discountOpt = discountRepo.findByVariantIdAndIsActiveTrue(variantId);

        if (discountOpt.isPresent()) {
            VariantDiscount discount = discountOpt.get();

            if (discount.getDiscountType() == DiscountType.PERCENT) {
                discountAmount = mrp * discount.getDiscountValue() / 100;
            } else {
                discountAmount = discount.getDiscountValue();
            }

            finalPrice = mrp - discountAmount;
        }

        VariantPricingResponseDto dto = new VariantPricingResponseDto();
        dto.setMrp(mrp);
        dto.setDiscount(discountAmount);
        dto.setFinalPrice(finalPrice);

        return dto;
    }
}
