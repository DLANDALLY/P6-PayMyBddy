package com.paymybuddy.paymybuddy.services.interfaces;

import com.paymybuddy.paymybuddy.entities.Transaction;

import java.util.List;

public interface ITransaction {
    List<Transaction> getAllTransaction();
    List<Transaction> getSenderTransaction();
    List<Transaction> getReceiverTransaction();

}
