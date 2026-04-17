package com.example.service;

import com.example.dto.BrandRequestDto;
import com.example.dto.BrandResponseDto;
import com.example.model.Brand;
import com.example.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper;

    // BULK CREATE
    public void createBrandsBulk(List<BrandRequestDto> dtos) {
        List<Brand> brands = dtos.stream()
                .map(dto -> modelMapper.map(dto, Brand.class))
                .collect(Collectors.toList());

        brandRepository.saveAll(brands);
    }

    // GET ALL ACTIVE BRANDS
    public List<BrandResponseDto> getAllBrands() {
        return brandRepository.findAll()
                .stream()
                .map(brand -> modelMapper.map(brand, BrandResponseDto.class))
                .collect(Collectors.toList());
    }
}
