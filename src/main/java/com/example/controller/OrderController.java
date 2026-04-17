package com.example.controller;

import com.example.dto.UpdateOrderStatusRequest;
import com.example.model.Order;
import com.example.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * User order history
     */
    @GetMapping("/user/{userId}")
    public List<Order> myOrders(@PathVariable Long userId) {
        return orderService.getOrdersByUser(userId);
    }

    /**
     * Track order by order number
     */
    @GetMapping("/{orderNumber}")
    public Order trackOrder(@PathVariable String orderNumber) {
        return orderService.getOrderByOrderNumber(orderNumber);
    }

    /**
     * Admin updates order status
     */
    @PutMapping("/{orderId}/status")
    public void updateStatus(
            @PathVariable Long orderId,
            @RequestBody UpdateOrderStatusRequest request
    ) {
        orderService.updateOrderStatus(orderId, request.getStatus());
    }
    
    
    @PutMapping("/{orderId}/cancel")
    public void cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
    }

    @PutMapping("/{orderId}/return")
    public void returnOrder(@PathVariable Long orderId) {
        orderService.requestReturn(orderId);
    }

    
}
