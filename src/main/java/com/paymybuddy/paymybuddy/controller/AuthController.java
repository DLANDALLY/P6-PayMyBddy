package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.form.RegisterForm;
import com.paymybuddy.paymybuddy.form.TransactionForm;
import com.paymybuddy.paymybuddy.services.interfaces.IUser;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
public class AuthController {
    @Autowired
    private IUser userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Enpoint Login
     */
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    //TODO : a faire - a connecter la la config
    @PostMapping("/login")
    public String handleLogin(@RequestParam String emailField,
                              @RequestParam String passwordField,
                              HttpSession session) {
        log.info("Login attempt");
        User user = userService.getUserByEmail(emailField);
        session.setAttribute("user", user);

        return "redirect:/profile";
    }

    /**
     * End point Registe
     */
    @GetMapping("/register")
    public String signIn(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }

    @PostMapping("/registerRequest")
    public String handleRegister(@Validated @ModelAttribute RegisterForm registerForm,
                                 BindingResult result, HttpSession session, Model model) {
        log.info("Register attempt");

        registerForm.setPassword(passwordEncoder.encode(registerForm.getPassword())); // Avoir si tjrs necessaire ??
        if (result.hasErrors()){
            return "/register";}

        //Creer un user et un compte
        boolean creauGreat = userService.createUserAndBankAccount(registerForm);


        User user = userService.getUserByEmail(registerForm.getEmail());
        System.out.println("######### start ###########");
        System.out.println("Creation de user et sa banque");
        System.out.println(" bool "+ creauGreat);
        System.out.println("User name = "+ user.getUsername());
        System.out.println("Compte id = "+ user.getBankAccount().getId());
        System.out.println("Compte id = "+ user.getBankAccount().getBalance());
        session.setAttribute("user", user);

        return "redirect:/profile";
    }

    /**
     * Enpoint Logout
     */
    @GetMapping("/logout")
    public String logout(SessionStatus sessionStatus) {
        sessionStatus.setComplete(); // Supprime l'objet "user" de la session
        return "redirect:/login"; // Redirige vers la page de login après déconnexion
    }

}
