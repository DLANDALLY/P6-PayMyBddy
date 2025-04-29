package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.entities.Transaction;
import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.repositories.TransactionRepository;
import com.paymybuddy.paymybuddy.services.interfaces.ITransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService implements ITransaction {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getAllTransaction(User sender) {
        return List.of();
    }

    @Override
    public List<Transaction> getSenderTransaction() {
        return List.of();
    }

    @Override
    public List<Transaction> getReceiverTransaction() {
        return List.of();
    }
}
