package com.paymybuddy.paymybuddy.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionForm {
    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email")
    private String email;

    @NotBlank(message = "The description is required")
    @NotEmpty(message = "The description is vide")
    private String description;

    @NotBlank(message = "The amount is required")
    private String amount;
}
