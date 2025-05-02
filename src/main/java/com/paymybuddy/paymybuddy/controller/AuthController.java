package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.services.interfaces.IUser;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@Slf4j
public class AuthController {
    @Autowired
    private IUser userService;

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


    //TODO : A faire
    @GetMapping("/logout")
    public String logout(SessionStatus sessionStatus) {
        sessionStatus.setComplete(); // Supprime l'objet "user" de la session
        return "redirect:/login"; // Redirige vers la page de login après déconnexion
    }
}
