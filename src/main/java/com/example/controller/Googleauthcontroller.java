package com.example.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.GoogleLoginRequest;

import com.example.service.GoogleAuthService;
import com.example.service.LoginService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class Googleauthcontroller {

	
  
    private final GoogleAuthService googleAuthService;


    @PostMapping("/google")
    public String googleLogin(@RequestBody GoogleLoginRequest req) {
        return googleAuthService.loginWithGoogle(req.getIdToken());
    }
	
	
}
