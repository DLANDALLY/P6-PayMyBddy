package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.entities.BankAccount;
import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.repositories.BankAccountRepository;
import com.paymybuddy.paymybuddy.services.interfaces.IBankAccount;
import com.paymybuddy.paymybuddy.services.interfaces.IUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class BankAccountServiceImpl implements IBankAccount {
    private BankAccountRepository bankAccountRepository;
    //private IUser userService;

    //TODO: Ajouter execption
    @Override
    public BankAccount getBankById(long bankId){
        return bankAccountRepository.findById(bankId)
                .orElseThrow(() -> new RuntimeException("ID bank aren't exist "+ bankId));
    }

    @Override
    public BankAccount updateBank(BankAccount bankAccount){
        BankAccount account = getBankById(bankAccount.getId());
        return bankAccountRepository.save(bankAccount);
    }


    @Override
    public BankAccount createBankAccount(){
        //User useDB = userService.getUserById(userId);
        //if (useDB == null) throw new RuntimeException("User is required for create a account");

        BankAccount bankAccount = BankAccount.builder()
                .id(UUID.randomUUID().hashCode())
                .balance(0)
                .active(true)
                //.user(useDB)
                .build();

        bankAccountRepository.save(bankAccount);

        return bankAccount;
    }

    public BankAccount getBankAccountByUserId(long userId){
        return null;
    }

    @Override
    public int getBankAccountSize() {
        return bankAccountRepository.findAll().size();
    }
}
