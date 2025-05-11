package com.paymybuddy.paymybuddy;

import com.paymybuddy.paymybuddy.form.RegisterForm;
import com.paymybuddy.paymybuddy.services.AuthService;
import com.paymybuddy.paymybuddy.services.UserDetailsServiceImpl;
import com.paymybuddy.paymybuddy.services.interfaces.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PaymybuddyApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymybuddyApplication.class, args);
	}

	//@Bean
	CommandLineRunner commandLineRunner(AuthService authService){
		RegisterForm r = new RegisterForm();
		r.setUsername("Test9");
		r.setPassword("123");
		r.setEmail("test9@hotmail.fr");

		return args -> {
			authService.addNewRole("USER");
			authService.addNewRole("ADMIN");
			authService.addNewUser(r);
			authService.addRoleToUser(r.getEmail(), "USER");
		};
	}
}
