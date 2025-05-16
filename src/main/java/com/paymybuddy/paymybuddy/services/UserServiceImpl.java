package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.entities.AppRole;
import com.paymybuddy.paymybuddy.entities.BankAccount;
import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.form.ProfileForm;
import com.paymybuddy.paymybuddy.form.RegisterForm;
import com.paymybuddy.paymybuddy.repositories.UserRepository;
import com.paymybuddy.paymybuddy.services.interfaces.IUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements IUser {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElse(null);
    }

    @Override
    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    @Override
    public Set<String> getConnectionEmails(User userSession) {
        User user = userRepository.findById(userSession.getId()).orElse(null);
        assert user != null;

        return user.getConnections().stream()
                .map(User::getEmail)
                .collect(Collectors.toSet());
    }


    @Override
    public User createUser(RegisterForm registerForm) {
        System.out.println("CreatUser() form test "+ registerForm.getEmail());
        User userDB = getUserByEmail(registerForm.getEmail());
        if (userDB != null) throw new RuntimeException("This user already exist");

        BankAccount bankAccount = BankAccount.builder()
                .balance(100)
                .active(true)
                .build();

        userDB = User.builder()
                .username(registerForm.getUsername())
                .email(registerForm.getEmail())
                .password(passwordEncoder.encode(registerForm.getPassword()))
                .bankAccount(bankAccount)
                .roles(List.of(new AppRole("USER")))
                .build();

        userRepository.save(userDB);
        return userDB;
    }

    @Override
    public User updateProfile(ProfileForm profileForm){
        User userBD = getUserById(profileForm.getId());

        if (Objects.equals(userBD.getUsername(), profileForm.getUsername()))
            throw new IllegalArgumentException("The new username must be different from the current one.");

        if (existsByEmail(profileForm.getEmail()))
            throw new IllegalArgumentException("A user with this email already exists.");

        if (passwordEncoder.matches(profileForm.getPassword(), userBD.getPassword()))
            throw new IllegalArgumentException("The new password must be different from the current one.");

        userBD.setUsername(profileForm.getUsername());
        userBD.setEmail(profileForm.getEmail());
        userBD.setPassword(passwordEncoder.encode(profileForm.getPassword()));

        return userRepository.save(userBD);
    }

    //Ah supp
    @Override
    public void updatePassword() {
        List<User> users = userRepository.findAll();
        users.stream()
                .map(u -> {
                    u.setPassword(passwordEncoder.encode("pass1234"));
                    return u;})
                .toList();
        userRepository.saveAll(users);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> searchByEmail(String keyword) {
        return userRepository.findByEmailContainingIgnoreCase(keyword);
    }

    @Override
    public User getUserById(long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No one user is found by this "+ id));
    }

    @Override
    public boolean AddNewRelation(User user, String email) {
        User userdb = getUserByEmail(email);
        User userSession = getUserById(user.getId());

        userSession.getConnections().add(userdb);
        userRepository.save(userSession);
        return false;
    }

    //TODO : Faut il que les users est mutuellement leurs email dans leur relation pour pouvoir s'envoyer de l'argent

}
