package com.paymybuddy.paymybuddy.services.interfaces;

import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.form.RegisterForm;

import java.util.List;
import java.util.Set;

public interface IUser {
    User getUserByEmail(String email);

    List<User> getAllUsers();

    List<User> searchByEmail(String keyword);

    User getUserById(long id);

    boolean isPresentUserById(long id);

    boolean AddNewRelation(User user, String keyword);

    Set<String> getConnectionEmails(User user);

    boolean createUserAndBankAccount(RegisterForm registerForm);

    User createUser(RegisterForm registerForm);
}
