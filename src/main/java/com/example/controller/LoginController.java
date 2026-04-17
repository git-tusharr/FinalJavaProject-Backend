package com.example.controller;


import com.example.dto.LoginRequest;



import com.example.service.LoginService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService authService;
   

@PostMapping("/login")
    public String login(@RequestBody LoginRequest req) {
        return authService.login(req);
    }

 
    
    
}
 