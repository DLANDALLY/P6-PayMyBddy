package com.paymybuddy.paymybuddy.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class ProfileForm {
    @NotBlank(message = "Username is required")
    @Length(max = 20, message = "Username not conform")
    private String username;

    @Email(message = "Please enter a valid email")
    private String email;

    @Length(min = 6, max = 50, message = "Password not conform")
    private String password;

    public ProfileForm(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
