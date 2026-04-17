package com.example.controller;

import com.example.model.OrderItem;
import com.example.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping("/{userId}")
    public List<OrderItem> checkout(@PathVariable Long userId) {
        return checkoutService.checkout(userId);
    }
    
    
    @GetMapping("/{userId}")
    public List<OrderItem> getOrders(@PathVariable Long userId) {
        return checkoutService.getOrders(userId);
    }
    
}
