package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dtos.transaction.TransactionDto;
import com.paymybuddy.paymybuddy.entities.Transaction;
import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.services.interfaces.ITransaction;
import com.paymybuddy.paymybuddy.services.interfaces.IUser;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;
import java.util.Set;

@Slf4j
@Controller
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private ITransaction transactionService;
    @Autowired
    private IUser userService;

    @GetMapping
    public String transaction(HttpSession session, Model model) {
        log.debug("transaction");
        User user = (User) session.getAttribute("user");
        List<TransactionDto> transactions = transactionService.getReceiverAndSenderTransaction(user);

        Set<String> userConnections = userService.getConnections(user);

        model.addAttribute("userConnections", userConnections);
        model.addAttribute("transactions", transactions);
        model.addAttribute("user", user);
        return "transaction";
    }
}
