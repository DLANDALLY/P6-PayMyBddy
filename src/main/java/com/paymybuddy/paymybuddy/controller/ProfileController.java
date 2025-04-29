package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("user")
public class ProfileController {

    @GetMapping("/profile")
    public String showProfile(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/addbuddy")
    public String addBuddy(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("user", user);
        return "relation";
    }
}
