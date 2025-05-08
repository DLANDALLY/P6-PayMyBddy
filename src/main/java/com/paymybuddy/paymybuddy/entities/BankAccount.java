package com.paymybuddy.paymybuddy.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "bank_accounts")
public class BankAccount {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double balance;
    private boolean active;

    @OneToOne(mappedBy = "bankAccount")
    private User user;

    public BankAccount() {
        this.active = true;
    }

    public BankAccount(User user) {
        this.active = true;
        this.user = user;
    }
}
