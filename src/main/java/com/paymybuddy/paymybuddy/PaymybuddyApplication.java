package com.paymybuddy.paymybuddy;

import com.paymybuddy.paymybuddy.form.RegisterForm;
import com.paymybuddy.paymybuddy.services.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymybuddyApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymybuddyApplication.class, args);
	}

	//@Bean
	CommandLineRunner commandLineRunner(AuthService authService){
		RegisterForm r = new RegisterForm();
		r.setUsername("admin");
		r.setPassword("admin");
		r.setEmail("admin@hotmail.fr");

		return args -> {
			//authService.addNewRole("USER");
			//authService.addNewRole("ADMIN");
			authService.addNewUser(r);
			authService.addRoleToUser(r.getEmail(), "USER");
		};
	}
}
