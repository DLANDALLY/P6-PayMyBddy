package com.paymybuddy.paymybuddy.services.interfaces;

import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.form.ProfileForm;
import com.paymybuddy.paymybuddy.form.RegisterForm;

import java.util.List;
import java.util.Set;

public interface IUser {
    User getUserByEmail(String email);

    List<User> getAllUsers();

    List<User> searchByEmail(String keyword);

    User getUserById(long id);

    boolean existsByEmail(String email);

    Set<String> getConnectionEmails(User user);

    User createUser(RegisterForm registerForm);

    User updateProfile(ProfileForm profileForm, long id);
}
