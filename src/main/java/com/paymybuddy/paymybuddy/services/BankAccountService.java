package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.entities.BankAccount;
import com.paymybuddy.paymybuddy.repositories.BankAccountRepository;
import com.paymybuddy.paymybuddy.services.interfaces.IBankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankAccountService implements IBankAccount {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    //TODO: Ajouter execption
    @Override
    public BankAccount getBankById(int bankId){
        return bankAccountRepository.findById(bankId).orElse(null);
    }

    public BankAccount updateBank(BankAccount bankAccount){
        return bankAccountRepository.save(bankAccount);
    }
}
