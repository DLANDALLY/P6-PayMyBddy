package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.repositories.UserRepository;
import com.paymybuddy.paymybuddy.services.interfaces.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUser {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
