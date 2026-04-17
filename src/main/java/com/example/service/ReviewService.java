package com.example.service;

import com.example.dto.RatingSummaryDto;
import com.example.dto.reeviewRequestDto;
import com.example.model.Review;
import com.example.repository.ReviewRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    // ✅ ADD REVIEW (WITH DUPLICATE PROTECTION)
    public void saveReview(reeviewRequestDto dto) {

        boolean alreadyReviewed =
                reviewRepository.existsByProductIdAndUserIdAndOrderNumber(
                        dto.getProductId(),
                        dto.getUserId(),
                        dto.getOrderNumber()
                );

        if (alreadyReviewed) {
            throw new RuntimeException("Review already submitted for this order");
        }

        Review review = new Review();
        review.setProductId(dto.getProductId());
        review.setUserId(dto.getUserId());
        review.setOrderNumber(dto.getOrderNumber());
        review.setRating(dto.getRating());
        review.setReviewText(dto.getReviewText());

        reviewRepository.save(review);
    }

    // ✅ GET REVIEWS BY PRODUCT
    public List<Review> getReviewsByProduct(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    // ✅ GET AVERAGE RATING
    public double getAverageRating(Long productId) {
        Double avg = reviewRepository.findAverageRatingByProductId(productId);
        return avg != null ? Math.round(avg * 10.0) / 10.0 : 0.0;
    }

    // ✅ GET RATING SUMMARY
    public RatingSummaryDto getRatingSummary(Long productId) {

        RatingSummaryDto dto = new RatingSummaryDto();

        Double avg = reviewRepository.findAverageRatingByProductId(productId);
        Long count = reviewRepository.countByProductId(productId);

        dto.setAverageRating(avg != null ? Math.round(avg * 10.0) / 10.0 : 0.0);
        dto.setTotalReviews(count != null ? count : 0L);

        return dto;
    }
}
