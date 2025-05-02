package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.entities.User;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Slf4j
@Controller
public class ProfileController {

    //TODO : a faire
    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "profile";
    }

    //TODO : à faire
    @GetMapping("/addbuddy")
    public String addBuddy(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "relation";
    }
}
