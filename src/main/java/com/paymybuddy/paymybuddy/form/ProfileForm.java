package com.paymybuddy.paymybuddy.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @Length(max = 50, message = "Email not conform")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Email format is invalid")
    private String email;

    @Length(min = 6, max = 50, message = "Password not conform")
    private String password;

    public ProfileForm(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
