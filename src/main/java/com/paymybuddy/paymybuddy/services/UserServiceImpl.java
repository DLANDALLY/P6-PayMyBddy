package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.entities.BankAccount;
import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.form.RegisterForm;
import com.paymybuddy.paymybuddy.repositories.UserRepository;
import com.paymybuddy.paymybuddy.services.interfaces.IUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements IUser {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElse(null);
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
    public boolean createUserAndBankAccount(RegisterForm registerForm) {
        User user = new User();
        user.setUsername(registerForm.getUsername());
        user.setEmail(registerForm.getEmail());
        user.setPassword(registerForm.getPassword());
        createUser(registerForm);
        //creatBankAccout(user);
        return true;
    }

    @Override
    public User createUser(RegisterForm registerForm) {
        System.out.println("form test "+ registerForm.getEmail());
        User userDB = getUserByEmail(registerForm.getEmail());
        if (userDB != null) throw new RuntimeException("This user already exist");

        BankAccount bankAccount = BankAccount.builder()
                .balance(0)
                .active(true)
                .build();

        userDB = User.builder()
                .username(registerForm.getUsername())
                .email(registerForm.getEmail())
                .password(passwordEncoder.encode(registerForm.getPassword()))
                .bankAccount(bankAccount)
                .build();

        userRepository.save(userDB);
        return userDB;
    }

//    public void creatBankAccout(User user){
//        int size = bankAccountRepository.getBankAccountSize();
//        BankAccount bankAccount = new BankAccount();
//        bankAccount.setUser(user);
//        bankAccount.setBalance(0);
//        bankAccount.setId(size + 1);
//        bankAccountRepository.createBankAccount(bankAccount);
//    }

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
    public boolean isPresentUserById(long id){
        return userRepository.findById(id).isPresent();
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
