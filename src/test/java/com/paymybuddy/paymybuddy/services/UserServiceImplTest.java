package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.form.ProfileForm;
import com.paymybuddy.paymybuddy.form.RegisterForm;
import com.paymybuddy.paymybuddy.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldGetUserByEmailSuccessful() {
        User newUser = createUserTest();
        User user = userService.getUserByEmail(newUser.getEmail());

        assertNotNull(user);
        assertEquals(newUser.getEmail(), user.getEmail());
        deletedUser(newUser);
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNotFound() {
        assertThrows(IllegalArgumentException.class, () ->
                userService.getUserByEmail("unknown@example.com"));
    }

    @Test
    void shouldExistsByEmailGetTrue() {
        User newUser = createUserTest();

        assertTrue(userService.existsByEmail(newUser.getEmail()));
        deletedUser(newUser);
    }

    @Test
    void shouldExistsByEmailGetFalse() {
        assertFalse(userService.existsByEmail("unknown@example.com"));
    }

    @Test
    void shouldGetConnectionEmailsSuccessful() {
        User newUser = createUserTest();
        User u1 = userRepository.findById(1L).orElse(null);
        User u2 = userRepository.findById(2L).orElse(null);
        User u3 = userRepository.findById(3L).orElse(null);

        Set<User> listConnect = Set.of(u1, u2, u3);
        newUser.setConnections(listConnect);
        userRepository.save(newUser);

        Set<String> emails = userService.getConnectionEmails(newUser.getId());
        User targetUser = userRepository.findById(newUser.getId()).orElse(null);

        assertNotNull(targetUser);
        assertEquals(3, emails.size());
        deletedUser(newUser);
    }

    @Test
    void shouldThrowExceptionWhenGetConnectionEmailsWithNotExistingId() {
        assertThrows(RuntimeException.class, () ->
                userService.getConnectionEmails(999L));
    }

    @Test
    void shouldCreateUserSuccessful() {
        User user = createUserTest();
        assertNotNull(userRepository.findById(user.getId()).orElse(null));
        deletedUser(user);
    }
    @Test
    void shouldThrowExceptionWhenUserAlreadyExisting() {
        User user = createUserTest();

        assertThrows(IllegalArgumentException.class, () ->
                userService.createUser(registerPerson()));
        deletedUser(user);
    }

    @Test
    void shouldUpdateProfileSuccessful() {
        User user = createUserTest();
        ProfileForm profileForm = profilUser();

        userService.updateProfile(profileForm, user.getId());
        User userUpdate = userRepository.findById(user.getId()).orElse(null);

        assertNotNull(userUpdate);
        assertEquals(userUpdate.getUsername(), profileForm.getUsername());
        assertEquals(userUpdate.getEmail(), profileForm.getEmail());
        deletedUser(user);

    }

    @Test
    void shouldGetAllUsersSuccessful() {
        int sizeUsers = userRepository.findAll().size();
        User user = createUserTest();

        assertEquals((sizeUsers + 1), userService.getAllUsers().size());
        deletedUser(user);
    }

    @Test
    void shouldGetUserByIdSuccessful() {
        User user = createUserTest();
        User userTest = userService.getUserById(user.getId());

        assertEquals(user.getUsername(), userTest.getUsername());
        assertEquals(user.getEmail(), userTest.getEmail());
        deletedUser(user);
    }

    @Test
    void shouldThrowExceptionWhenIdNotFound() {
        assertThrows(RuntimeException.class, () -> userService.getUserById(999L));
    }

    @Test
    void shouldUpdateUserConnexionSuccessful() {
        userRepository.deleteById(58L);
        User newUser = createUserTest();
        User u1 = userService.getUserById(1L);

        userService.updateUserConnexion(newUser, u1);
        User userTest = userRepository.findById(newUser.getId()).orElse(null);

        assertNotNull(userTest);
        assertEquals( 1, userTest.getConnections().size());
        deletedUser(newUser);
    }

    RegisterForm registerPerson(){
        RegisterForm registerForm = new RegisterForm();
        registerForm.setUsername("userTest");
        registerForm.setEmail("usertest@example.com");
        registerForm.setPassword("pass1234");
        return registerForm;
    }

    void deletedUser(User newUser){
        userRepository.delete(newUser);
    }

    User createUserTest(){
        RegisterForm registerForm = registerPerson();
        return userService.createUser(registerForm);
    }

    ProfileForm profilUser(){
        ProfileForm profileForm = new ProfileForm();
        profileForm.setUsername("testUnitaire");
        profileForm.setEmail("testUnitaire@example.com");
        profileForm.setPassword("pass1234");
        return  profileForm;
    }
}