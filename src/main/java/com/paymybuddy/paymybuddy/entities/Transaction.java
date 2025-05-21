package com.paymybuddy.paymybuddy.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "transactions", indexes = {
        @Index(name = "idx_sender", columnList = "sender_id"),
        @Index(name = "idx_receiver", columnList = "receiver_id")
})
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    //@Column(precision = 10, scale = 2, nullable = false)
    private Double amount;

    private String description;
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    public Transaction() {
        this.date = LocalDateTime.now();
    }
}
