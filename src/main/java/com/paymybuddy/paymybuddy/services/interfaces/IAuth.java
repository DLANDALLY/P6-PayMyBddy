package com.paymybuddy.paymybuddy.services.interfaces;

import com.paymybuddy.paymybuddy.entities.AppRole;
import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.form.RegisterForm;

public interface IAuth {
    User addNewUser(RegisterForm registerForm);

    AppRole addNewRole(String role);

    void addRoleToUser(String email, String role);
}
