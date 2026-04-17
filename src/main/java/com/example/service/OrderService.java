package com.example.service;

import com.example.model.Order;
import com.example.model.OrderItem;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrdertrackingRepository;
import com.example.util.OrderNumberGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrdertrackingRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final ObjectMapper objectMapper;

    /**
     * Called AFTER payment success
     * ✔ Moves full checkout snapshot into orders
     * ✔ Clears checkout data
     */
    @Transactional
    public Order createOrderFromCheckout(Long userId, Long paymentId) {

        // 1️⃣ Fetch checkout (temporary) data
        List<OrderItem> checkoutItems = orderItemRepo.findByUserId(userId);

        if (checkoutItems.isEmpty()) {
            throw new RuntimeException("No checkout data found for user: " + userId);
        }

        // 2️⃣ Serialize checkout → JSON
        String itemsJson;
        try {
            itemsJson = objectMapper.writeValueAsString(checkoutItems);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize checkout items", e);
        }

        // 3️⃣ Create FINAL order
        Order order = new Order();
        order.setOrderNumber(OrderNumberGenerator.generate());
        order.setUserId(userId);
        order.setPaymentId(paymentId);
        order.setOrderStatus("CONFIRMED");
        order.setItemsJson(itemsJson);

        Order savedOrder = orderRepo.save(order);

        // 4️⃣ Clear checkout table ✅
        orderItemRepo.deleteByUserId(userId);

        return savedOrder;
    }

    /**
     * Fetch all orders for a user (Order History)
     */
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepo.findByUserId(userId);
    }

    /**
     * Track order using order number
     */
    public Order getOrderByOrderNumber(String orderNumber) {
        return orderRepo.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    /**
     * Admin / system status update
     */
    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setOrderStatus(status);
        orderRepo.save(order);
    }
    
    public void cancelOrder(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getOrderStatus().equals("CONFIRMED") &&
            !order.getOrderStatus().equals("PACKED")) {
            throw new RuntimeException("Order cannot be cancelled at this stage");
        }

        order.setOrderStatus("CANCELLED");
        orderRepo.save(order);
    }

    public void requestReturn(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getOrderStatus().equals("DELIVERED")) {
            throw new RuntimeException("Return allowed only after delivery");
        }

        order.setOrderStatus("RETURN_REQUESTED");
        orderRepo.save(order);
    }
}
