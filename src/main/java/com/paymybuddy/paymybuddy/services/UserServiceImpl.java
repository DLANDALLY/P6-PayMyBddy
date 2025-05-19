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
                .orElseThrow(() -> new IllegalArgumentException("Email not found"));
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
    public User updateProfile(ProfileForm profileForm, long id){
        User userBD = getUserById(id);

        if (!Objects.equals(userBD.getUsername(), profileForm.getUsername()))
            userBD.setUsername(profileForm.getUsername());
        //throw new IllegalArgumentException("The new username must be different from the current one.");

        if (!existsByEmail(profileForm.getEmail()))
            userBD.setEmail(profileForm.getEmail());
        //throw new IllegalArgumentException("A user with this email already exists.");

        if (!passwordEncoder.matches(profileForm.getPassword(), userBD.getPassword()))
            userBD.setPassword(passwordEncoder.encode(profileForm.getPassword()));
        //throw new IllegalArgumentException("The new password must be different from the current one.");

        ProfileForm userMapper = modelMapper.map(userBD, ProfileForm.class);
        if (Objects.equals(userMapper, profileForm))
            throw new IllegalArgumentException("The new username must be different from the current one.");

        return userRepository.save(userBD);
    }

    //Ah supp
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
}
