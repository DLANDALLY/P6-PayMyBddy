package com.paymybuddy.paymybuddy.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bank_accounts")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double balance;
    private boolean active;

    @OneToOne(mappedBy = "bankAccount")
    private User user;
}
