package com.paymybuddy.paymybuddy.services.interfaces;

import com.paymybuddy.paymybuddy.entities.AppRole;
import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.form.RegisterForm;
import org.springframework.transaction.annotation.Transactional;

public interface IAuth {
    User addNewUser(RegisterForm registerForm);

    AppRole addNewRole(String role);

    void addRoleToUser(String email, String role);

    @Transactional
    void removeRoleFromUser(String email, String role);

    User loadUserByUsername(String email);

    User getUserByEmail(String emailField);
}
