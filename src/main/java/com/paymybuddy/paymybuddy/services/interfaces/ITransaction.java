package com.paymybuddy.paymybuddy.services.interfaces;

import com.paymybuddy.paymybuddy.dtos.transaction.TransactionDto;
import com.paymybuddy.paymybuddy.entities.Transaction;
import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.form.TransactionForm;

import java.util.List;

public interface ITransaction {
    List<TransactionDto> getReceiverAndSenderTransaction(User user);

    List<Transaction> getTransactionBySender(User user);

    List<Transaction> getTransactionByReceiver(User user);

    void getTransaction(User session, TransactionForm transactionForm);
}
