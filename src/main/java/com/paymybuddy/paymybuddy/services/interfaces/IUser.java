package com.paymybuddy.paymybuddy.services.interfaces;

import com.paymybuddy.paymybuddy.entities.User;

import java.util.Set;

public interface IUser {
    User getUserByEmail(String email);

    Set<String> getConnections(User user);
}
