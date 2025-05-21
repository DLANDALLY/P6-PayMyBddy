package com.paymybuddy.paymybuddy.services.interfaces;

import com.paymybuddy.paymybuddy.entities.BankAccount;

public interface IBankAccount {
    BankAccount getBankById(long bankId);

    void updateBank(BankAccount bankAccount);
}
