package com.example.service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Service;


@Service
public class WebhookService {

    public boolean verify(String payload, String signature, String secret) {
        try {
            Mac sha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256.init(key);
            byte[] digest = sha256.doFinal(payload.getBytes());

            // Convert byte[] to HEX to match Razorpay signature
            StringBuilder hash = new StringBuilder();
            for (byte b : digest) {
                hash.append(String.format("%02x", b));
            }

            return hash.toString().equals(signature);
        } catch (Exception e) {
            System.out.println("Webhook verification failed: " + e.getMessage());
            return false;
        }
    }
}
