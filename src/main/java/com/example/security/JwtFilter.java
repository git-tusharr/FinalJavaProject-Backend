package com.example.security;

import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.PermissionService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PermissionService permissionService;

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain)
            throws ServletException, IOException {

        String path = req.getRequestURI();

        // ✅ ✅ SKIP PUBLIC APIs (VERY IMPORTANT)
        if (path.startsWith("/auth/login") ||
            path.startsWith("/auth/register") ||
            path.startsWith("/auth/verify-otp") ||
            path.startsWith("/auth/google")) {

            chain.doFilter(req, res);
            return;
        }

        String header = req.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);

            if (jwtUtil.isValid(token)) {

                String email = jwtUtil.extractEmail(token);

                User user = userRepository.findByEmail(email).orElse(null);

                if (user != null) {

                    Set<String> perms =
                            permissionService.getUserPermissions(user.getId());

                    Set<GrantedAuthority> authorities = new HashSet<>();
                    for (String perm : perms) {
                        authorities.add(new SimpleGrantedAuthority(perm));
                    }

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    user, null, authorities
                            );

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(auth);
                }
            }
        }

        chain.doFilter(req, res);
    }
}