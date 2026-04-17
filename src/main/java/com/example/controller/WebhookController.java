package com.example.controller;

import com.example.service.PaymentService;
import com.example.service.WebhookService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class WebhookController {

    private final WebhookService webhookService;
    private final PaymentService paymentService;

    @Value("${razorpay.webhook_secret}")
    private String WEBHOOK_SECRET;

    @PostMapping("/webhook/razorpay")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload,
                                                @RequestHeader("X-Razorpay-Signature") String signature) {

    	
    	System.out.println("Webhook HIT: payload received!");

        // 1️⃣ Security: Verify payload using HMAC-SHA256
        if (!webhookService.verify(payload, signature, WEBHOOK_SECRET)) {
            System.out.println("Webhook signature FAILED");
            return ResponseEntity.status(401).body("Invalid signature");
        }

        // 2️⃣ Convert JSON payload
        JSONObject json = new JSONObject(payload);
        String event = json.getString("event");

        // 3️⃣ Get data object
        JSONObject entity = json
                .getJSONObject("payload")
                .getJSONObject(event.contains("subscription") ? "subscription" : "payment")
                .getJSONObject("entity");

        System.out.println("\n===== WEBHOOK RECEIVED =====");
        System.out.println("Event: " + event);
        System.out.println(entity);
        System.out.println("============================\n");

        // 4️⃣ Handle cases
        switch (event) {
            case "payment.captured":
                paymentService.orderPaid(entity.getString("order_id"), entity.getString("id"));
                break;

            case "subscription.activated":
            case "subscription.charged":
                paymentService.subscriptionPaid(entity.getString("id"));
                break;

            case "subscription.halted":
            case "subscription.pending":
                paymentService.subscriptionHalted(entity.getString("id"));
                break;

            case "subscription.cancelled":
                paymentService.subscriptionHalted(entity.getString("id"));
                break;

            default:
                System.out.println("Ignoring unsupported event: " + event);
        }

        return ResponseEntity.ok("OK");
    }
}
