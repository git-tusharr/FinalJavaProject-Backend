package com.example.controller;

import com.example.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Subscription;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Value("${razorpay.key_id}")
    private String KEY;

    @Value("${razorpay.key_secret}")
    private String SECRET;

    @Value("${razorpay.plan_id}")
    private String PLAN_ID;

    @PostMapping("/create-order")
    public Map<String,String> createOrder(@RequestBody Map<String,Object> data) throws Exception {
        RazorpayClient client = new RazorpayClient(KEY, SECRET);


        int amount = ((Number)data.get("amount")).intValue();
        Long userId = Long.valueOf(data.get("userId").toString());

        JSONObject req = new JSONObject();
        req.put("amount", amount);
        req.put("currency", "INR");
        req.put("payment_capture", 1);

        Order order = client.orders.create(req);

        paymentService.orderCreated(userId, order.get("id"));

        return Map.of("orderId", order.get("id"), "key", KEY);
    }

    @PostMapping("/create-subscription")
    public Map<String,String> createSub(@RequestBody Map<String,Object> data) throws Exception {
    	System.out.println("PLAN from props = [" + PLAN_ID + "] (" + PLAN_ID.length() + ")");

        RazorpayClient client = new RazorpayClient(KEY, SECRET);

        Long userId = Long.valueOf(data.get("userId").toString());

        JSONObject req = new JSONObject();
        req.put("plan_id", PLAN_ID);
        req.put("customer_notify", 1);
        req.put("total_count", 12);

        Subscription sub = client.subscriptions.create(req);

        paymentService.subscriptionCreated(userId, sub.get("id"));

        return Map.of("subscriptionId", sub.get("id"), "key", KEY);
    }
    
    @GetMapping("/status/order/{orderId}")
    public String getOrderStatus(@PathVariable String orderId) {
        return paymentService.getOrderStatus(orderId);
    }

    @GetMapping("/status/subscription/{subId}")
    public String getSubscriptionStatus(@PathVariable String subId) {
        return paymentService.getSubscriptionStatus(subId);
    }
    
    @PostMapping("/order-paid")
    public void orderPaid(@RequestBody Map<String, String> data) {
        String orderId = data.get("orderId");
        String paymentId = data.get("paymentId");
        paymentService.orderPaid(orderId, paymentId);
    }

}
