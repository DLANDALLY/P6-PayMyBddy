package com.paymybuddy.paymybuddy.services.interfaces;

import com.paymybuddy.paymybuddy.entities.User;

import java.util.List;
import java.util.Set;

public interface IUser {
    User getUserByEmail(String email);

    List<User> getAllUsers();

    List<User> searchByEmail(String keyword);

    User getUserById(int id);

    boolean AddNewRelation(User user, String keyword);

    Set<String> getConnectionEmails(User user);
}
