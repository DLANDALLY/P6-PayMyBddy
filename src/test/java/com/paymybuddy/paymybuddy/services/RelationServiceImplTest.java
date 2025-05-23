package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.form.RegisterForm;
import com.paymybuddy.paymybuddy.repositories.UserRepository;
import com.paymybuddy.paymybuddy.services.interfaces.IUser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        RegisterForm registerForm = newPerson();
        User userCreate = userService.createUser(registerForm);
        long id = 1L;

        relationService.addNewRelation(id, userCreate.getEmail());
        User user = userService.getUserById(id);

        assertTrue(user.getConnections().contains(userCreate));
        resetUserConnection(user, userCreate);
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
    void shouldThrowExceptionWhenAlreadyConnected() {
        RegisterForm registerForm = newPerson();
        User userCreate = userService.createUser(registerForm);
        long id = 1L;

        relationService.addNewRelation(id, userCreate.getEmail());
        User user = userService.getUserById(id);

        assertThrows(Exception.class, ()->
            relationService.addNewRelation(id, userCreate.getEmail()));
        resetUserConnection(user, userCreate);
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

    RegisterForm newPerson(){
        RegisterForm registerForm = newPerson();
        registerForm.setUsername("userTest");
        registerForm.setEmail("usertest@example.com");
        registerForm.setPassword("pass1234");
        return registerForm;
    }

    void resetUserConnection(User user, User userCreate){
        user.getConnections().remove(userCreate);
        userRepository.save(user);
    }


}