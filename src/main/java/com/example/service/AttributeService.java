package com.example.service;

import com.example.dto.AttributeRequestDto;
import com.example.dto.AttributeResponseDto;
import com.example.model.Attribute;
import com.example.model.AttributeValue;
import com.example.repository.AttributeRepository;
import com.example.repository.AttributeValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttributeService {

    private final AttributeRepository attributeRepo;
    private final AttributeValueRepository valueRepo;

    // CREATE ATTRIBUTE + VALUES
    public void createAttribute(AttributeRequestDto dto) {

        Attribute attribute = new Attribute();
        attribute.setName(dto.getName());
        attributeRepo.save(attribute);

        for (String val : dto.getValues()) {
            AttributeValue value = new AttributeValue();
            value.setAttributeId(attribute.getId());
            value.setValue(val);
            valueRepo.save(value);
        }
    }

    // GET SINGLE ATTRIBUTE WITH VALUES
    public AttributeResponseDto getAttribute(Long attributeId) {

        Attribute attribute = attributeRepo.findById(attributeId)
                .orElseThrow(() -> new RuntimeException("Attribute not found"));

        List<AttributeValue> values =
                valueRepo.findByAttributeId(attributeId);

        AttributeResponseDto res = new AttributeResponseDto();
        res.setId(attribute.getId());
        res.setName(attribute.getName());
        res.setValues(values);

        return res;
    }

    // GET ALL ATTRIBUTES WITH VALUES
    public List<AttributeResponseDto> getAllAttributes() {

        return attributeRepo.findAll().stream().map(attribute -> {

            List<AttributeValue> values =
                    valueRepo.findByAttributeId(attribute.getId());

            AttributeResponseDto dto = new AttributeResponseDto();
            dto.setId(attribute.getId());
            dto.setName(attribute.getName());
            dto.setValues(values);

            return dto;
        }).toList();
    }
}
