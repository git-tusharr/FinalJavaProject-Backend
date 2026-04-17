package com.example.controller;

import com.example.model.ProductVideo;
import com.example.service.ProductVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductVideoController {

    private final ProductVideoService videoService;

    // ⭐ BULK ADD
    @PostMapping("/{productId}/videos")
    public ResponseEntity<List<ProductVideo>> addBulkVideos(
            @PathVariable Long productId,
            @RequestBody List<ProductVideo> reqList) {

        return ResponseEntity.ok(videoService.addBulk(productId, reqList));
    }

    // GET ALL
    @GetMapping("/{productId}/videos")
    public ResponseEntity<List<ProductVideo>> getVideos(
            @PathVariable Long productId) {

        return ResponseEntity.ok(videoService.getByProduct(productId));
    }
}
