package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.model.User;
import com.example.service.RegistrationService;

@RestController
@RequestMapping("/auth")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/register")
    public String register(@RequestBody User user){
        registrationService.registerUser(user);
        return "User registered. Verify email & phone OTPs.";
    }

    @PostMapping("/verify-email-otp")
    public String verifyEmail(@RequestParam String email, @RequestParam String otp){
        boolean success = registrationService.verifyEmailOTP(email, otp);
        return success ? "Email verified" : "Invalid or expired OTP";
    }

    @PostMapping("/verify-phone-otp")
    public String verifyPhone(@RequestParam String phone, @RequestParam String otp){
    	
        boolean success = registrationService.verifyPhoneOTP(phone, otp);
        return success ? "Phone verified" : "Invalid or expired OTP";
    }
}


