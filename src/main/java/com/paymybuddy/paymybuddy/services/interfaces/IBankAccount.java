package com.paymybuddy.paymybuddy.services.interfaces;

import com.paymybuddy.paymybuddy.entities.BankAccount;

public interface IBankAccount {
    //TODO: Ajouter execption
    BankAccount getBankById(int bankId);

    BankAccount updateBank(BankAccount bankAccount);

    void createBankAccount(BankAccount bankAccount);

    int getBankAccountSize();
}
