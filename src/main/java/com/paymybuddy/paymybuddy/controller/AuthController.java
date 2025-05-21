package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.form.RegisterForm;
import com.paymybuddy.paymybuddy.form.TransactionForm;
import com.paymybuddy.paymybuddy.services.interfaces.IAuth;
import com.paymybuddy.paymybuddy.services.interfaces.IUser;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Map;

@Slf4j
@Controller
@AllArgsConstructor
public class AuthController {
    private IUser userService;
    private IAuth authservice;

    //TODO : FT = renommer toutes les methods
    //TODO : FT = Renommer les cles etrangere - ajouter les + (fichier init.sql)

    //TODO : FT = ajouter un message de confirmation d'enregistrement
    //TODO : FT = Ajouter un try catch a tout les endpoints
    //TODO : FT = Ajout de message d'erreur dans le cas ou l'utilisateur senvoi de l'argent a lui meme ou le retirer de la liste

    /**
     * Enpoint Login
     */
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    /**
     * End point Registe
     */
    @GetMapping("/register")
    public String signIn(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null)
            model.addAttribute("username", username);

        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }

    @GetMapping("/registerbygithub")
    public String signInByGitHub(OAuth2AuthenticationToken authenticationToken, Model model) {
        Map<String, Object> attributes = authenticationToken.getPrincipal().getAttributes();
        String username = (String) attributes.get("login");

        model.addAttribute("registerForm", new RegisterForm());
        model.addAttribute("username", username);
        return "register";
    }

    @PostMapping("/registerRequest")
    public String handleRegister(@Validated @ModelAttribute RegisterForm registerForm,
                                 BindingResult result, HttpSession session, Model model) {
        log.info("Register attempt");
        if (result.hasErrors()) return "register";

        try {
            User user = authservice.addNewUser(registerForm);
            System.out.println("## Register username "+ user.getUsername());
            System.out.println("## Register username "+ user.getEmail());
            session.setAttribute("user", user);

            return "redirect:/profile";
        }catch (RuntimeException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
    }

    /**
     * Enpoint Logout
     */
    @GetMapping("/logout")
    public String logout(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "redirect:/login";
    }
}
