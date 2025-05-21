package com.paymybuddy.paymybuddy.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@Table(name = "bank_accounts")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double balance;
    private boolean active;

    @OneToOne(mappedBy = "bankAccount")
    private User user;

    public BankAccount() {
        this.active = true;
    }
}
