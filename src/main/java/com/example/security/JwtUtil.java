package com.example.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.model.Role;
import com.example.model.User;
import com.example.model.UserRole;
import com.example.repository.RoleRepository;
import com.example.repository.UserRoleRepository;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    private final Key key;
    private final long expiration;
   
    @Autowired
 UserRoleRepository userRoleRepo;
    @Autowired
 RoleRepository roleRepo;

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.expiration}") long expiration) {
    	
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expiration = expiration;
        
    }
    
 
    

    public String generateToken(User user) {
    	
    	List<UserRole> userRoles = userRoleRepo.findByUserId(user.getId());

    	List<String> roles = new ArrayList<>();

    	for (UserRole ur : userRoles) {
    	    Role role = roleRepo.findById(ur.getRoleId()).orElseThrow();
    	    roles.add(role.getName());
    	}

    	 
        return Jwts.builder()
            .setSubject(user.getEmail())
            .claim("userId", user.getId())
            .claim("roles", roles)
            .claim("username", user.getUsername()) 
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    public boolean isValid(String token) {
        try { 
        getClaims(token); 
        return true; 
        }
       catch (Exception e) { return false; }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
    }
}