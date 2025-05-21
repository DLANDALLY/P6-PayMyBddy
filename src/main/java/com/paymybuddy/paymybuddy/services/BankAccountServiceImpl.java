package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.entities.BankAccount;
import com.paymybuddy.paymybuddy.repositories.BankAccountRepository;
import com.paymybuddy.paymybuddy.services.interfaces.IBankAccount;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BankAccountServiceImpl implements IBankAccount {
    private BankAccountRepository bankAccountRepository;

    @Override
    public BankAccount getBankById(long bankId){
        return bankAccountRepository.findById(bankId)
                .orElseThrow(() -> new RuntimeException("ID bank aren't exist "+ bankId));
    }

    @Override
    public void updateBank(BankAccount bankAccount) {
        if (bankAccount == null)
            throw new IllegalArgumentException("Bank account must not be null");

        BankAccount existing = getBankById(bankAccount.getId());
        if (existing == null)
            throw new EntityNotFoundException("Bank account not found with ID: " + bankAccount.getId());

        bankAccountRepository.save(bankAccount);
    }
}
