package com.paymybuddy.paymybuddy.entities;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@Builder
@Entity
public class AppRole {
    @Id
    private String role;

    public AppRole(String role) {
        this.role = role;
    }
}
