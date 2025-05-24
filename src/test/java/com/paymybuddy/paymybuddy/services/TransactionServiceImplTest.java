package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.dtos.transaction.TransactionDto;
import com.paymybuddy.paymybuddy.entities.Transaction;
import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.form.TransactionForm;
import com.paymybuddy.paymybuddy.repositories.BankAccountRepository;
import com.paymybuddy.paymybuddy.repositories.TransactionRepository;
import com.paymybuddy.paymybuddy.repositories.UserRepository;
import com.paymybuddy.paymybuddy.services.interfaces.IBankAccount;
import com.paymybuddy.paymybuddy.services.interfaces.IUser;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class TransactionServiceImplTest {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private IBankAccount bankAccountService;
    @Autowired
    private IUser userService;
    @Autowired
    private TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldGetAllUserTransactionsSuccessful() {
        User user = userService.getUserById(1L);
        TransactionForm transactionForm = newTransaction();
        int size = transactionService.getAllUserTransactions(user).size();

        transactionService.processTransaction(user, transactionForm);
        List<TransactionDto> transactions = transactionService.getAllUserTransactions(user);
        double montant = Math.abs(transactions.get(1).getAmount());

        assertEquals(size + 2, transactions.size());
        assertEquals(transactionForm.getAmount(), montant);
        assertEquals(transactionForm.getDescription(), transactions.get(1).getDescription());
    }

    @Test
    void shouldThrowExceptionWhenUserIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            transactionService.getAllUserTransactions(new User());
        });
    }

    @Test
    void shouldProcessTransactionSuccessful() {
        User user = userService.getUserById(1L);
        TransactionForm transactionForm = newTransaction();

        transactionService.processTransaction(user, transactionForm);

        List<TransactionDto> transactions = transactionService.getAllUserTransactions(user);
        double balance = Math.abs(transactions.get(1).getAmount());

        assertEquals(transactionForm.getAmount(), balance);
        assertEquals(transactionForm.getDescription(), transactions.get(1).getDescription());
    }

    @Test
    void shouldThrowExceptionWhenProcessingTransactionWithNullUser() {
        TransactionForm transactionForm = newTransaction();

        assertThrows(RuntimeException.class, () -> {
            transactionService.processTransaction(new User(), transactionForm);
        });
    }

    @Test
    void shouldThrowExceptionWhenProcessingTransactionWithNullTransactionForm() {
        User user = userService.getUserById(1L);

        assertThrows(IllegalArgumentException.class, () -> {
            transactionService.processTransaction(user, new TransactionForm());
        });
    }

    TransactionForm newTransaction(){
        double randomAmount = Math.round((0.01 + Math.random() * (0.99 - 0.01)) * 100.0) / 100.0;
        User user = userService.getUserById(2L);
        TransactionForm transactionForm = new TransactionForm();
        transactionForm.setEmail(user.getEmail());
        transactionForm.setDescription("test unitaire");
        transactionForm.setAmount(randomAmount);
        return transactionForm;
    }

}