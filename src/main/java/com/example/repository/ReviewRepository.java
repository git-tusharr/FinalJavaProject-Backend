package com.example.repository;

import com.example.model.Review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	   List<Review> findByProductId(Long productId);
	   
	   // ✅ AVERAGE RATING
	    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.productId = :productId")
	    Double findAverageRatingByProductId(@Param("productId") Long productId);
	
	    
	    Long countByProductId(Long productId);
	    
	    
	    boolean existsByProductIdAndUserIdAndOrderNumber(
	            Long productId,
	            Long userId,
	            String orderNumber
	    );
}


