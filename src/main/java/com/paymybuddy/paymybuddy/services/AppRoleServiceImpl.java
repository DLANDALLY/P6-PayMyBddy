package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.entities.AppRole;
import com.paymybuddy.paymybuddy.repositories.AppRoleRepository;
import com.paymybuddy.paymybuddy.services.interfaces.IAppRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        AppRole appRole = getRoleById(role);
        if(appRole != null) throw new RuntimeException("This role already exist");

        appRole = AppRole.builder().role(role).build();
        appRoleRepository.save(appRole);

        return appRole;
    }
}
