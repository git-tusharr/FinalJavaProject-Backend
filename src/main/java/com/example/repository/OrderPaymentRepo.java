package com.example.repository;

import com.example.model.OrderPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OrderPaymentRepo extends JpaRepository<OrderPayment, Long> {
    Optional<OrderPayment> findByRazorpayOrderId(String orderId);
}
