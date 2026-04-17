package com.example.service;

import com.example.model.*;
import com.example.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final UserRoleRepository userRoleRepo;
    private final RolePermissionRepository rolePermRepo;
    private final PermissionRepository permissionRepo;
    
    
    
    public Permission createPermission(String code, String module) {
        Permission p = new Permission();
        p.setCode(code);
        p.setModule(module);
        return permissionRepo.save(p);
    }

    public List<Permission> getAllPermissions() {
        return permissionRepo.findAll();
    }
    
    

    // 🔑 MAIN METHOD USED BY JWT FILTER
    public Set<String> getUserPermissions(Long userId) {

        Set<String> permissions = new HashSet<>();

        List<UserRole> userRoles =
                userRoleRepo.findByUserId(userId);

        for (UserRole ur : userRoles) {

            List<RolePermission> rolePerms =
                    rolePermRepo.findByRoleId(ur.getRoleId());

            for (RolePermission rp : rolePerms) {

                permissionRepo.findById(rp.getPermissionId())
                        .ifPresent(p -> permissions.add(p.getCode()));
            }
        }
        return permissions;
    }
}
//user----user-role---role---role-permission--permisiionid-code

