package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.entities.BankAccount;
import com.paymybuddy.paymybuddy.repositories.BankAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BankAccountServiceImplTest {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private BankAccountServiceImpl bankAccountService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldGetBankById() {
        assertNotNull(bankAccountService.getBankById(1L));
    }

    @Test
    void shouldNotGetBankById() {
        assertThrows(RuntimeException.class, () -> {
            bankAccountService.getBankById(999L);
        });
    }

    @Test
    void shouldUpdateBank() {
        bankAccountRepository.findById(1L).ifPresent(b -> {
            b.setBalance(987654);
            bankAccountRepository.save(b);});

        double updatedBalance = bankAccountRepository.findById(1L)
                .map(BankAccount::getBalance)
                .orElseThrow();

        assertEquals(987654, updatedBalance);
    }
}