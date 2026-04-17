package com.example.service;

import com.example.dto.*;
import com.example.model.ProductReview;
import com.example.repository.ProductReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductReviewService {

    private final ProductReviewRepository repo;

    // ADD REVIEW
    public void addReview(
            Long productId,
            Long userId,
            ReviewRequestDto dto
    ) {

        if (dto.getRating() < 1 || dto.getRating() > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }

        ProductReview review = new ProductReview();
        review.setProductId(productId);
        review.setUserId(userId);
        review.setRating(dto.getRating());
        review.setReview(dto.getReview());

        repo.save(review);
    }

    // GET REVIEWS
    public List<ReviewResponseDto> getReviews(Long productId) {

        return repo.findByProductId(productId)
                .stream()
                .map(r -> {
                    ReviewResponseDto dto = new ReviewResponseDto();
                    dto.setId(r.getId());
                    dto.setRating(r.getRating());
                    dto.setReview(r.getReview());
                    dto.setUserId(r.getUserId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // GET RATING SUMMARY
    public RatingSummaryDto getRatingSummary(Long productId) {

        RatingSummaryDto dto = new RatingSummaryDto();
        dto.setAverageRating(
                repo.getAverageRating(productId) == null
                        ? 0.0
                        : repo.getAverageRating(productId)
        );
        dto.setTotalReviews(
                repo.countByProductId(productId)
        );

        return dto;
    }
    
    public Double getAverageRating(Long productId) {
        return repo.getAverageRating(productId);
    }


    
}
