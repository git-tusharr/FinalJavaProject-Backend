package com.example.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;       // User login email

    @Column(nullable = false)
    private String password;    // Encrypted password

    @Column(nullable = true, unique = true)
    private String username;    // Optional username

    @Column(nullable = true, unique = true)
    private String phone;       // Optional phone
    

    private boolean emailVerified = false;
    private boolean phoneVerified = false;

    private String status = "PENDING"; // PENDING or ACTIVE
    
    
}
