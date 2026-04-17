package com.example.controller;

import com.example.dto.ForgotPasswordRequest;
import com.example.dto.ResetPasswordRequest;
import com.example.service.ForgotPasswordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    public ForgotPasswordController(ForgotPasswordService forgotPasswordService) {
        this.forgotPasswordService = forgotPasswordService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @RequestBody ForgotPasswordRequest request) {

        forgotPasswordService.forgotPassword(request);
        return ResponseEntity.ok("If email exists, reset link has been sent");
    }

    @GetMapping("/reset-password/validate")
    public ResponseEntity<String> validate(@RequestParam String token) {

        forgotPasswordService.validateToken(token);
        return ResponseEntity.ok("Token is valid");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestBody ResetPasswordRequest request) {

        forgotPasswordService.resetPassword(request);
        return ResponseEntity.ok("Password reset successful");
    }
}
