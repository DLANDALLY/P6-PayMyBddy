package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.entities.AppRole;
import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.form.RegisterForm;
import com.paymybuddy.paymybuddy.services.interfaces.IAppRole;
import com.paymybuddy.paymybuddy.services.interfaces.IAuth;
import com.paymybuddy.paymybuddy.services.interfaces.IUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthService implements IAuth {
    private IUser userService;
    private IAppRole appRoleService;

    @Override
    public User addNewUser(RegisterForm registerForm){
        return userService.createUser(registerForm);
    }

    @Override
    public AppRole addNewRole(String role){
        return appRoleService.createRole(role);
    }

    @Transactional
    @Override
    public void addRoleToUser(String email, String role){
        User user = userService.getUserByEmail(email);
        AppRole appRole = appRoleService.getRoleById(role);
        user.getRoles().add(appRole);
    }
}
