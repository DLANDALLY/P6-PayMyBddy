package com.paymybuddy.paymybuddy.repositories;

import com.paymybuddy.paymybuddy.entities.Transaction;
import com.paymybuddy.paymybuddy.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findTransactionsBySender(User sender);

    List<Transaction> findTransactionsByReceiver(User receiver);

    List<Transaction> findTransactionsByReceiverAndSender(User receiver, User sender);
}
