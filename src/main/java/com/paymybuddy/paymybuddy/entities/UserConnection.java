package com.paymybuddy.paymybuddy.entities;

import jakarta.persistence.*;

@Entity
public class UserConnection {
    @EmbeddedId
    private UserConnectionId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("friendId")
    private User friend;
}
