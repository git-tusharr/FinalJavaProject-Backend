package com.example.controller;


import com.example.dto.reeviewRequestDto;
import com.example.model.Review;
import com.example.service.ReviewService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/reviews")
@CrossOrigin
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<?> addReview(@RequestBody reeviewRequestDto dto) {
        try {
            reviewService.saveReview(dto);
            return ResponseEntity.ok("Review added successfully");
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @GetMapping("/product/{productId}")
    public List<Review> getReviews(@PathVariable Long productId) {
        return reviewService.getReviewsByProduct(productId);
    }
    
    // ✅ GET AVERAGE RATING
    @GetMapping("/product/{productId}/average")
    public double getAverageRating(@PathVariable Long productId) {
        return reviewService.getAverageRating(productId);
    }
    
}
