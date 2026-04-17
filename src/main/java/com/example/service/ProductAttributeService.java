package com.example.service;

import com.example.dto.AttributeResponseDto;
import com.example.model.Attribute;
import com.example.model.AttributeValue;
import com.example.model.ProductAttribute;
import com.example.repository.AttributeRepository;
import com.example.repository.AttributeValueRepository;
import com.example.repository.ProductAttributeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductAttributeService {

    private final ProductAttributeRepository repository;
    private final AttributeRepository attributeRepository;
    private final AttributeValueRepository attributeValueRepository;

    // ✅ ASSIGN ATTRIBUTES TO PRODUCT
    public void assignAttributesToProduct(
            Long productId,
            com.example.dto.ProductAttributeRequestDto dto
    ) {
        repository.deleteByProductId(productId);

        for (Long attributeId : dto.getAttributeIds()) {
            ProductAttribute pa = new ProductAttribute();
            pa.setProductId(productId);
            pa.setAttributeId(attributeId);
            repository.save(pa);
        }
    }

    // ✅ GET ATTRIBUTES + VALUES FOR PRODUCT (IMPORTANT)
    public List<AttributeResponseDto> getAttributesByProduct(Long productId) {

        return repository.findByProductId(productId)
                .stream()
                .map(pa -> {

                    Attribute attribute = attributeRepository
                            .findById(pa.getAttributeId())
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Attribute not found: " + pa.getAttributeId()
                                    )
                            );

                    List<AttributeValue> values =
                            attributeValueRepository.findByAttributeId(attribute.getId());

                    AttributeResponseDto dto = new AttributeResponseDto();
                    dto.setId(attribute.getId());
                    dto.setName(attribute.getName());
                    dto.setValues(values); // ✅ FULL VALUE OBJECTS

                    return dto;
                })
                .collect(Collectors.toList());
    }
}
