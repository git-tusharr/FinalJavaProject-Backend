package com.example.controller;

import com.example.model.ProductAdditionalInfo;
import com.example.service.ProductAdditionalInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductAdditionalInfoController {

    private final ProductAdditionalInfoService infoService;

    // ⭐ BULK ADD
    @PostMapping("/{productId}/additional-info")
    public ResponseEntity<List<ProductAdditionalInfo>> addBulkAdditionalInfo(
            @PathVariable Long productId,
            @RequestBody List<ProductAdditionalInfo> reqList) {

        return ResponseEntity.ok(infoService.addBulk(productId, reqList));
    }

    // GET ALL
    @GetMapping("/{productId}/additional-info")
    public ResponseEntity<List<ProductAdditionalInfo>> getAdditionalInfo(
            @PathVariable Long productId) {

        return ResponseEntity.ok(infoService.getByProduct(productId));
    }
}
