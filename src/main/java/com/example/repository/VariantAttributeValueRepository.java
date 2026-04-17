package com.example.repository;


import com.example.model.VariantAttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VariantAttributeValueRepository
        extends JpaRepository<VariantAttributeValue, Long> {

    List<VariantAttributeValue> findByVariantId(Long variantId);

    List<VariantAttributeValue> findByAttributeValueId(Long attributeValueId);
    
    List<VariantAttributeValue> findByAttributeValueIdIn(List<Long> ids);

}
