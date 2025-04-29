package com.paymybuddy.paymybuddy.services.interfaces;

import com.paymybuddy.paymybuddy.entities.Transaction;
import com.paymybuddy.paymybuddy.entities.User;

import java.util.List;

public interface ITransaction {
    List<Transaction> getAllTransaction(User sender);
    List<Transaction> getSenderTransaction();
    List<Transaction> getReceiverTransaction();

}
