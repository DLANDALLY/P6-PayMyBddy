package com.paymybuddy.paymybuddy.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/relation")
public class RelationController {

    @GetMapping
    public String getRelation(HttpSession session, Model model){
        return "/relation";
    }

}
