package com.paymybuddy.paymybuddy.dtos.transaction;

import com.paymybuddy.paymybuddy.entities.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReceiverDto extends TransactionDto {
    private User receiver;
}
