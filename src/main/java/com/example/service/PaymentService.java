package com.example.service;

import com.example.dto.CreateOrderRequest;
import com.example.model.OrderPayment;
import com.example.model.SubscriptionRecord;
import com.example.repository.OrderPaymentRepo;
import com.example.repository.SubscriptionRecordRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderPaymentRepo orderRepo;
    private final SubscriptionRecordRepo subRepo;
    private final OrderService orderService; 
    
    public void orderCreated(Long userId, String orderId) {
        OrderPayment order = new OrderPayment();
        order.setUserId(userId);
        order.setRazorpayOrderId(orderId);
        order.setStatus("CREATED");
        orderRepo.save(order);
    }

//    public void orderPaid(String orderId, String paymentId){
//        OrderPayment order = orderRepo.findByRazorpayOrderId(orderId)
//                .orElseGet(() -> {
//                    OrderPayment op = new OrderPayment();
//                    op.setRazorpayOrderId(orderId);
//                    op.setStatus("CREATED");  // fallback
//                    return op;
//                });
//
//        order.setRazorpayPaymentId(paymentId);
//        order.setStatus("PAID");
//        order.setPaidAt(LocalDateTime.now());
//        OrderPayment saved= orderRepo.save(order);
//        
//        orderService.createOrderFromCheckout(
//                saved.getUserId(),
//                saved.getId()
//        );
//    }
    
    public void orderPaid(String orderId, String paymentId) {
        // ✅ FIX: Never use orElseGet here — it creates a userId-less record
        OrderPayment order = orderRepo.findByRazorpayOrderId(orderId)
                .orElseThrow(() -> new RuntimeException(
                    "OrderPayment not found for orderId: " + orderId));

        order.setRazorpayPaymentId(paymentId);
        order.setStatus("PAID");
        order.setPaidAt(LocalDateTime.now());
        OrderPayment saved = orderRepo.save(order);

        // ✅ Now userId is guaranteed to exist
        orderService.createOrderFromCheckout(
                saved.getUserId(),
                saved.getId()
        );
    }


    public void subscriptionCreated(Long userId, String subId){
        SubscriptionRecord sr = new SubscriptionRecord();
        sr.setUserId(userId);
        sr.setSubscriptionId(subId);
        sr.setStatus("PENDING");
        subRepo.save(sr);
    }

    public void subscriptionPaid(String subId){
        SubscriptionRecord sr = subRepo.findBySubscriptionId(subId).orElseThrow();
        sr.setStatus("ACTIVE");
        sr.setLastPaidAt(LocalDateTime.now());
        subRepo.save(sr);
    }

    public void subscriptionHalted(String subId){
        SubscriptionRecord sr = subRepo.findBySubscriptionId(subId).orElseThrow();
        sr.setStatus("HALTED");
        subRepo.save(sr);
    }
    
    public String getOrderStatus(String orderId){
        return orderRepo.findByRazorpayOrderId(orderId)
                .map(OrderPayment::getStatus)
                .orElse("NOT_FOUND");
    }

    public String getSubscriptionStatus(String subId){
        return subRepo.findBySubscriptionId(subId)
                .map(SubscriptionRecord::getStatus)
                .orElse("NOT_FOUND");
    }

}
