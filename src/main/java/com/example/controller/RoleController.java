package com.example.controller;

import com.example.model.Role;
import com.example.model.User;
import com.example.service.RoleService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/auth/rbac/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    
    // CREATE ROLE
   // @PreAuthorize("hasAuthority('role:create')")
    @PostMapping
    public Role create(@RequestBody Role role) {
        System.out.println("ROLE NAME: " + role.getName()); // 👈 ADD HERE
        return roleService.createRole(role.getName());
    }

    // LIST ROLES
    @GetMapping
    public List<Role> list() {
        return roleService.getAllRoles();
    }
    


}
