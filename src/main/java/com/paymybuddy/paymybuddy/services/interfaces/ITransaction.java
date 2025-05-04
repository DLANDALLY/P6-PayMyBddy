package com.paymybuddy.paymybuddy.services.interfaces;

import com.paymybuddy.paymybuddy.dtos.transaction.TransactionDto;
import com.paymybuddy.paymybuddy.entities.Transaction;
import com.paymybuddy.paymybuddy.entities.User;

import java.util.List;

public interface ITransaction {
    //List<Transaction> getAllTransaction(User user);
    //List<Transaction> getSenderTransaction(User user);
    //List<Transaction> getReceiverTransaction(User user);

    List<TransactionDto> getReceiverAndSenderTransaction(User user);

    List<Transaction> getTransactionBySender(User user);

    List<Transaction> getTransactionByReceiver(User user);
}
