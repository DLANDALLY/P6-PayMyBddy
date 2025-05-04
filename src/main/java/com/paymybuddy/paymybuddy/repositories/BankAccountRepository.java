package com.paymybuddy.paymybuddy.repositories;

import com.paymybuddy.paymybuddy.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {

}
