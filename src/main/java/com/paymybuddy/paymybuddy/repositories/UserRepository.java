package com.paymybuddy.paymybuddy.repositories;

import com.paymybuddy.paymybuddy.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByEmail(String email);
}
