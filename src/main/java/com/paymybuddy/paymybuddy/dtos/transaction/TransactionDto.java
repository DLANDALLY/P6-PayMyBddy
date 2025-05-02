package com.paymybuddy.paymybuddy.dtos.transaction;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class TransactionDto {
    private String username;
    private String description;
    private Double amount;
}
