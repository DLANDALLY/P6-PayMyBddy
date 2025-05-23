package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.form.RegisterForm;
import com.paymybuddy.paymybuddy.repositories.UserRepository;
import com.paymybuddy.paymybuddy.services.interfaces.IUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {
    @Autowired
    private IUser userService;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldAddNewUser() {
        RegisterForm registerForm = new RegisterForm();
        registerForm.setUsername("user"+ generateNumber());
        registerForm.setEmail("user"+generateNumber()+"@example.fr");
        registerForm.setPassword("userPassword");

        authService.addNewUser(registerForm);
        User user = userRepository.findUserByEmail(registerForm.getEmail())
                .orElse(null);

        assertNotNull(user);
        assertEquals(registerForm.getUsername(), user.getUsername());
        assertEquals(registerForm.getEmail(), user.getEmail());
        deletedUser(user);
    }

    @Test
    void shouldNotAddNewUser(){
        RegisterForm registerForm = new RegisterForm();
        registerForm.setUsername("alice");
        registerForm.setEmail("alice@example.com");
        registerForm.setPassword("userPassword");

        assertThrows(IllegalArgumentException.class, () -> {
            authService.addNewUser(registerForm);});
    }

    int generateNumber() {
        Random random = new Random();
        return random.nextInt(1000 - 1 + 1) + 1;
    }

    void deletedUser(User newUser){
        userRepository.delete(newUser);
    }
}