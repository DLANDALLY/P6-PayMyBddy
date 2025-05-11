package com.paymybuddy.paymybuddy.services.interfaces;

import com.paymybuddy.paymybuddy.entities.AppRole;


public interface IAppRole {
    AppRole getRoleById(String role);

    AppRole createRole(String role);
}
