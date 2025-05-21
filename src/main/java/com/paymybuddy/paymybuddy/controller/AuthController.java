package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.form.RegisterForm;
import com.paymybuddy.paymybuddy.services.interfaces.IAuth;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private IAuth authservice;

    //TODO : FT = renommer toutes les methods
    //TODO : FT = Renommer les cles etrangere - ajouter les + (fichier init.sql)

    //TODO : FT = ajouter un message de confirmation d'enregistrement
    //TODO : FT = Ajouter un try catch a tout les endpoints

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
        log.info("GET Register");
        String username = (String) session.getAttribute("username");
        if (username != null)
            model.addAttribute("username", username);

        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }

    @GetMapping("/registerbygithub")
    public String signInByGitHub(OAuth2AuthenticationToken authenticationToken, Model model) {
        log.info("GET Register by github");
        Map<String, Object> attributes = authenticationToken.getPrincipal().getAttributes();
        String username = (String) attributes.get("login");

        model.addAttribute("registerForm", new RegisterForm());
        model.addAttribute("username", username);
        return "register";
    }

    @PostMapping("/registerRequest")
    public String handleRegister(@Validated @ModelAttribute RegisterForm registerForm,
                                 BindingResult result, HttpSession session, Model model) {
        log.info("POST Register");
        if (result.hasErrors()) return "register";

        try {
            User user = authservice.addNewUser(registerForm);
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
        log.info("GET Logout");
        sessionStatus.setComplete();
        return "redirect:/login";
    }
}
