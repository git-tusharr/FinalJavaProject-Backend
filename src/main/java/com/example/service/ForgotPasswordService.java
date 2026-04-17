package com.example.service;

import com.example.dto.ForgotPasswordRequest;
import com.example.dto.ResetPasswordRequest;
import com.example.model.User;
import com.example.repository.UserRepository;

import com.example.util.EmailServicereg;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ForgotPasswordService {

    private final UserRepository userRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final EmailServicereg emailService;
    private final PasswordEncoder passwordEncoder;

    public ForgotPasswordService(UserRepository userRepository,
                                 RedisTemplate<String, String> redisTemplate,
                                 EmailServicereg emailService,
                                 PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    // STEP 1: Send magic link
    public void forgotPassword(ForgotPasswordRequest request) {

        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {

            String token = UUID.randomUUID().toString();
            String redisKey = "reset:" + token;

            redisTemplate.opsForValue()
            .set(redisKey, user.getId().toString(), 15, TimeUnit.MINUTES);

            String resetLink = "http://localhost:5173/reset-password?token=" + token;
            emailService.sendEmail(
                    user.getEmail(),
                    "Reset Your Password",
                    "Click the link below to reset your password:\n\n"
                            + resetLink
                            + "\n\nThis link is valid for 15 minutes."
            );

        });

        // Do NOT expose whether email exists
    }

    // STEP 2: Validate token
    public void validateToken(String token) {

        String redisKey = "reset:" + token;
        String userId = redisTemplate.opsForValue().get(redisKey);

        if (userId == null) {
            throw new RuntimeException("Invalid or expired reset link");
        }
    }

    // STEP 3: Reset password
    public void resetPassword(ResetPasswordRequest request) {

        String redisKey = "reset:" + request.getToken();
        String userId = redisTemplate.opsForValue().get(redisKey);

        if (userId == null) {
            throw new RuntimeException("Invalid or expired reset link");
        }

        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        // one-time link
        redisTemplate.delete(redisKey);
    }
}
