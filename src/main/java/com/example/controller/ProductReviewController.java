package com.example.controller;

import com.example.dto.*;
import com.example.service.ProductReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/{productId}/reviews")
@RequiredArgsConstructor
public class ProductReviewController {

    private final ProductReviewService service;

    // ADD REVIEW (Logged-in user)
    @PostMapping
    public String addReview(
            @PathVariable Long productId,
            @RequestParam Long userId,   // later from JWT
            @RequestBody ReviewRequestDto dto
    ) {
        service.addReview(productId, userId, dto);
        return "Review submitted";
    }

    // GET ALL REVIEWS
    @GetMapping
    public List<ReviewResponseDto> getReviews(
            @PathVariable Long productId
    ) {
        return service.getReviews(productId);
    }

    // GET RATING SUMMARY
    @GetMapping("/summary")
    public RatingSummaryDto getRatingSummary(
            @PathVariable Long productId
    ) {
        return service.getRatingSummary(productId);
    }
}
