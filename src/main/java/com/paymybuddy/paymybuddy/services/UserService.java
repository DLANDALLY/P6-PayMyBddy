package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.repositories.UserRepository;
import com.paymybuddy.paymybuddy.services.interfaces.IUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService implements IUser {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public Set<String> getConnections(User userSession) {
        System.out.println("### get connection");
        User user = userRepository.findById(userSession.getId()).orElse(null);
        assert user != null;
        Set<String> connections = user.getConnections().stream()
                .map(User::getEmail)
                .collect(Collectors.toSet());

        connections.forEach(System.out::println);
        return connections ;
    }

    //TODO : Faut il que les users est mutuellement leurs email dans leur relation pour pouvoir s'envoyer de l'argent

}
