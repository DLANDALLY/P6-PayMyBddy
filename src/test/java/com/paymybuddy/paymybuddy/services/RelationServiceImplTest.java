package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.form.RegisterForm;
import com.paymybuddy.paymybuddy.form.TransactionForm;
import com.paymybuddy.paymybuddy.repositories.UserRepository;
import com.paymybuddy.paymybuddy.services.interfaces.IUser;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RelationServiceImplTest {
    @Autowired
    private IUser userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RelationServiceImpl relationService;

    @Test
    void shouldAddNewRelationSuccessfully() {
        User userCreate = createUser();
        String emailToConnect = "alice@example.com";

        relationService.addNewRelation(userCreate.getId(), emailToConnect);
        User reloadedUser = userService.getUserById(userCreate.getId());

        assertEquals(1, reloadedUser.getConnections().size());
        deletedUser(userCreate);
    }

    @Test
    void shouldThrowExceptionWhenIdIsMissing() {
        assertThrows(IllegalArgumentException.class, ()-> {
            relationService.addNewRelation(0, "usertest@example.com");
        });
    }

    @Test
    void shouldThrowExceptionWhenEmailIsMissing() {
        assertThrows(IllegalArgumentException.class, ()-> {
            relationService.addNewRelation(1L, " ");
        });
    }

    @Test
    void cannotAddRelationYourself() {
        User user = userService.getUserById(1L);

        assertThrows(Exception.class, ()->
            relationService.addNewRelation(user.getId(), user.getEmail()));
    }

    @Test
    void shouldFilterUsersWithoutConnection() {
        User user = userService.getUserById(1L);
        Set<User> connectedUsers = user.getConnections();
        Set<User> usersConnect = userService.getAllUsers().stream()
                .filter(u -> !connectedUsers.contains(u) && u.getId() != 1L)
                .collect(Collectors.toSet());

        assertFalse(usersConnect.contains(user));
        for (User connected : connectedUsers) {
            assertFalse(usersConnect.contains(connected));
        }
    }

    User createUser(){
        RegisterForm registerForm = newPerson();
        return userService.createUser(registerForm);
    }


    RegisterForm newPerson(){
        RegisterForm registerForm = new RegisterForm();
        registerForm.setUsername("userTest"+ generateNumber());
        registerForm.setEmail("usertest"+generateNumber()+"@example.com");
        registerForm.setPassword("pass1234");
        return registerForm;
    }
    int generateNumber() {
        Random random = new Random();
        return random.nextInt(1000 - 1 + 1) + 1;
    }

    void deletedUser(User user){
        userRepository.delete(user);
    }


}