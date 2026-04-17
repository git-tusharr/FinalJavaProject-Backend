package com.example.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dto.RegisterRequest;
import com.example.dto.LoginRequest;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;



    public String login(LoginRequest req) {

    	String identifier = req.getIdentifier();
    	
        User user = userRepository.findByEmailOrPhoneOrUsername(identifier, identifier, identifier)
                .orElseThrow(() -> new RuntimeException("Invalid Email"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }

        return jwtUtil.generateToken(user);
    }
}
