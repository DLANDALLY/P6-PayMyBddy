package com.paymybuddy.paymybuddy.repositories;

import com.paymybuddy.paymybuddy.entities.Transaction;
import com.paymybuddy.paymybuddy.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> getTransactionsBySender(User sender);

    List<Transaction> sender(User sender);
}
