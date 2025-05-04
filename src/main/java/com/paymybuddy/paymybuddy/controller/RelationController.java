package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.services.interfaces.IUser;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/relation")
public class RelationController {
    @Autowired
    private IUser userService;

    @GetMapping("test")
    public String showRelation(HttpSession session, Model model){
        return "/relation";
    }

//    @PostMapping
//    public String searchRelation(@RequestParam(value = "keyword", required = false) String keyword, Model model){
//        List<User> users;
//        if (keyword != null && !keyword.isBlank()) users = userService.searchByEmail(keyword);
//        else users = userService.getAllUsers();
//
//        model.addAttribute("users", users);
//        model.addAttribute("keyword", keyword);
//        return "/relation";
//    }

    @PostMapping
    public String searchRelation(@RequestParam(value = "keyword", required = false) String keyword,
                                 Model model,
                                 HttpSession session){
        User user = (User) session.getAttribute("user");
        List<User> users;
        boolean addRelation = userService.AddNewRelation(user, keyword);
        if (keyword != null && !keyword.isBlank()) users = userService.searchByEmail(keyword);
        else users = userService.getAllUsers();

        model.addAttribute("users", users);
        model.addAttribute("keyword", keyword);
        return "redirect:/transaction";
    }

    @GetMapping
    public String searchUsers(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<User> users = userService.getAllUsers();

        model.addAttribute("users", users);
        model.addAttribute("keyword", keyword);
        return "/relation";
    }


}
