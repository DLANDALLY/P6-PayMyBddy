package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.entities.BankAccount;
import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.form.RegisterForm;
import com.paymybuddy.paymybuddy.repositories.UserRepository;
import com.paymybuddy.paymybuddy.services.interfaces.IBankAccount;
import com.paymybuddy.paymybuddy.services.interfaces.IUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService implements IUser {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IBankAccount bankAccountRepository;

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
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
        createUser(user);
        creatBankAccout(user);
        return true;
    }

    public void createUser(User user){
        userRepository.save(user);
    }

    public void creatBankAccout(User user){
        int size = bankAccountRepository.getBankAccountSize();
        BankAccount bankAccount = new BankAccount();
        bankAccount.setUser(user);
        bankAccount.setBalance(0);
        bankAccount.setId(size + 1);
        bankAccountRepository.createBankAccount(bankAccount);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> searchByEmail(String keyword) {
        return userRepository.findByEmailContainingIgnoreCase(keyword);
    }

    @Override
    public User getUserById(int id){
        return userRepository.findById(id).get();
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
