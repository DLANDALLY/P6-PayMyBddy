package com.paymybuddy.paymybuddy.form;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class TransactionForm {
    @Email(message = "Please enter a valid email")
    @Length(max = 50, message = "Email not conform")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Email format is invalid")
    private String email;

    @NotBlank(message = "The description is required")
    private String description;

    @NotNull(message = "The amount is required")
    @Positive(message = "Amount must be greater than zero")
    private double amount;
}
