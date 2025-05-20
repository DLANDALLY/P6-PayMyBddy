package com.paymybuddy.paymybuddy.services.interfaces;

import com.paymybuddy.paymybuddy.entities.User;

import java.util.List;

public interface IRelation {
    void addNewRelation(long id, String email);

    List<User> filterUsersWithoutConnection(long userId);
}
