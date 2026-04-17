package com.example.repository;

import com.example.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // Fetch checkout data for a user
    List<OrderItem> findByUserId(Long userId);

    // Clear checkout data after successful payment
    void deleteByUserId(Long userId);
}
