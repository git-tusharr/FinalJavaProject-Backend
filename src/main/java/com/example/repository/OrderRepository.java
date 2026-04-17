package com.example.repository;

import com.example.model.Order;
import com.example.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderItem, Long> {
	   List<OrderItem> findByUserId(Long userId);
	   
	   
	 
}
