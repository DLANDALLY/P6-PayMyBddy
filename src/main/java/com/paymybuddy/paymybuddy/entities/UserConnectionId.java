package com.paymybuddy.paymybuddy.entities;

import jakarta.persistence.Embeddable;

@Embeddable
public class UserConnectionId {
    private Integer userId;
    private Integer friendId;
}
