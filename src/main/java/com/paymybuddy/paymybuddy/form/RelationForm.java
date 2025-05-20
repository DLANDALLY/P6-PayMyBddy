package com.paymybuddy.paymybuddy.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class RelationForm {
    @Email(message = "Please enter a valid email")
    @NotBlank(message = "Email is required")
    @Length(max = 50, message = "Email not conform")
    private String email;
}
