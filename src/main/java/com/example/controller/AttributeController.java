package com.example.controller;

import com.example.dto.AttributeRequestDto;
import com.example.dto.AttributeResponseDto;
import com.example.service.AttributeService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attributes")
@RequiredArgsConstructor
public class AttributeController {

    private final AttributeService service;

    @PostMapping
    public String createAttribute(@RequestBody AttributeRequestDto dto) {
        service.createAttribute(dto);
        return "Attribute created";
    }

    @GetMapping
    public List<AttributeResponseDto> getAllAttributes() {
        return service.getAllAttributes();
    }
    
    @GetMapping("/{id}")
    public AttributeResponseDto getAttribute(@PathVariable Long id) {
        return service.getAttribute(id);
    }
}
