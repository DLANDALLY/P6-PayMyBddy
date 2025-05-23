package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.services.interfaces.IRelation;
import com.paymybuddy.paymybuddy.services.interfaces.IUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RelationServiceImpl implements IRelation {
    private IUser userService;

    @Override
    public void addNewRelation(long id, String email){
        if (id == 0)
            throw new IllegalArgumentException("ID does not exist");
        User userBD = userService.getUserById(id);

        if (email.isBlank())
            throw new IllegalArgumentException("Email parameter is missing");
        User newPerson = userService.getUserByEmail(email);

        boolean isConnexion = userBD.getConnections().contains(newPerson);
        if (isConnexion)
            throw new IllegalArgumentException("Users are already connected");
        if (userBD.getEmail().equals(newPerson.getEmail()))
            throw new IllegalArgumentException("You cannot add yourself");

        userService.updateUserConnexion(userBD, newPerson);
    }

    @Override
    public List<User> filterUsersWithoutConnection(long userId){
        Set<User> connectedUsers = userService.getUserById(userId).getConnections();

        return userService.getAllUsers().stream()
                .filter(user ->
                        !connectedUsers.contains(user) &&
                        user.getId() != userId)
                .collect(Collectors.toList());
    }
}
