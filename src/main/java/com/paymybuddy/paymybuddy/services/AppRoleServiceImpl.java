package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.entities.AppRole;
import com.paymybuddy.paymybuddy.repositories.AppRoleRepository;
import com.paymybuddy.paymybuddy.services.interfaces.IAppRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppRoleServiceImpl implements IAppRole {
    private AppRoleRepository appRoleRepository;

    @Override
    public AppRole getRoleById(String role) {
        return appRoleRepository.findById(role)
                .orElse(null);
    }

    @Override
    public AppRole createRole(String role) {
        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("Role name must not be null or blank");
        }

        AppRole existingRole = getRoleById(role);
        if (existingRole != null) {
            throw new RuntimeException("This role already exists");
        }

        AppRole newRole = AppRole.builder().role(role).build();
        return appRoleRepository.save(newRole);
    }
}
