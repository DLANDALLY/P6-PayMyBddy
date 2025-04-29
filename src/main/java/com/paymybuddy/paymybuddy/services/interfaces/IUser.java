package com.paymybuddy.paymybuddy.services.interfaces;

import com.paymybuddy.paymybuddy.entities.User;

public interface IUser {
    User getUserByEmail(String email);
}
