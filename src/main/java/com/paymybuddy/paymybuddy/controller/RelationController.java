package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.form.RelationForm;
import com.paymybuddy.paymybuddy.services.interfaces.IRelation;
import com.paymybuddy.paymybuddy.services.interfaces.IUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/relation")
@AllArgsConstructor
public class RelationController {
    private IUser userService;
    private IRelation relationService;

    @PostMapping
    public String searchRelation(@Valid @ModelAttribute RelationForm relationForm, BindingResult result,
                                 Model model, HttpSession session){
        log.info("POST searchRealtion");
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        if (result.hasErrors()){
            getAllUsers(model, user.getId());
            return "relation";
        }

        try{
            userService.existsByEmail(relationForm.getEmail());
            relationService.addNewRelation(user.getId(), relationForm.getEmail());

            return "redirect:/transaction";
        }catch (IllegalArgumentException iae){
            getAllUsers(model, user.getId());
            model.addAttribute("emailNotFound", iae.getMessage());

            return "relation";
        }
    }

    @GetMapping
    public String searchUsers(Model model, HttpSession session) {
        log.info("GET search users");
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        getAllUsers(model, user.getId());
        model.addAttribute("relationForm", new RelationForm());
        return "relation";
    }

    private void getAllUsers(Model model, long userId){
        List<User> users = relationService.filterUsersWithoutConnection(userId);
        model.addAttribute("users", users);
    }
}
