package com.paymybuddy.paymybuddy.dtos.transaction;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class SenderDto {
    private String username;
    private String description;
    private Double amount;
    private LocalDateTime date;
}
