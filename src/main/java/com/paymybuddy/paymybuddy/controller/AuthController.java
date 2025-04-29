package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.services.interfaces.IUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("user")
@Slf4j
public class AuthController {
    @Autowired
    private IUser userService;

    @ModelAttribute("user")
    public User setUpUser() {
        return new User();
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String email,
                              @RequestParam String password,
                              @ModelAttribute("user") User user) {
        log.info("Login attempt");
        user = userService.getUserByEmail(email);
        log.info("User found"+ user.getEmail());

        //user.setEmail(user.getEmail());
        return "redirect:/profil";
    }

    @GetMapping("/logout")
    public String logout(SessionStatus sessionStatus) {
        sessionStatus.setComplete(); // Supprime l'objet "user" de la session
        return "redirect:/login"; // Redirige vers la page de login après déconnexion
    }
}
