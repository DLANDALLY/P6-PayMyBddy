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

    //TODO : BUG = Probleme de transf "calcule tax"
    //TODO renommer toutes les methods
    //TODO : FT = Renommer les cles etrangere - ajouter les +
    //TODO : FT = redirige l'user lorsqu'il se trompe d'URL
    //TODO : FT = ajout message derreur GETMAPPING erreor
    //TODO : FT = ajouter un message de confirmation d'enregistrement
    //TODO : FT = Ajouter la dependance de boostrap
    //TODO : BUG = lorsque je click sur valide le formulaire vide la page est rediriger

    /**
     * Enpoint Login
     */
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    //Le login est gerer par spring security (a supp)
    @PostMapping("/login")
    public String handleLogin(@RequestParam String emailField,
                              @RequestParam String passwordField,
                              HttpSession session) {
        log.info("Login attempt");
        User user = userService.getUserByEmail(emailField);
        if(user != null){
            session.setAttribute("user", user);
            return "redirect:/profile";
        }

        session.setAttribute("error", "Un probleme ses produit lors de la connection");
        return "redirect:/profile";
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
        if (result.hasErrors()) return "/registerRequest";

        //Creer un user et un compte
        User user = authservice.addNewUser(registerForm);
        if (user == null) {
            model.addAttribute("error", "Un probleme est survenu");
            return "/registerRequest";}

        session.setAttribute("user", user);
        return "redirect:/profile";
    }

    /**
     * Enpoint Logout
     */
    @GetMapping("/logout")
    public String logout(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "redirect:/login";
    }

    @GetMapping("/error")
    public String errePage(HttpSession session, Model model){
        //if (message != null) model.addAttribute("error", message);

        return "/error";
    }

}
