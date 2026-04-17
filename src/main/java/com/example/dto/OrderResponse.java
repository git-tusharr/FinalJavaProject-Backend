package com.example.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OrderResponse {

    private String orderNumber;
    private String orderStatus;
    private Long checkoutId;
    private Long paymentId;
    private LocalDateTime createdAt;

    // getters and setters
}
