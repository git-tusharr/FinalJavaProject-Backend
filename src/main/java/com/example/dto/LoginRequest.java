package com.example.dto;

import lombok.Data;

@Data

public class LoginRequest {
    private String identifier; // email OR phone OR username
    private String password;
}
