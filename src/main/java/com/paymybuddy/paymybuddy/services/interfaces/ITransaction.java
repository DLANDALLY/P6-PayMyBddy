package com.paymybuddy.paymybuddy.services.interfaces;

import com.paymybuddy.paymybuddy.dtos.transaction.TransactionDto;
import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.form.TransactionForm;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ITransaction {

    List<TransactionDto> getAllUserTransactions(User user);

    @Transactional
    void processTransaction(User session, TransactionForm form);
}
