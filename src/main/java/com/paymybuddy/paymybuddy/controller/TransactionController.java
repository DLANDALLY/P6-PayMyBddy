package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dtos.transaction.TransactionDto;
import com.paymybuddy.paymybuddy.entities.Transaction;
import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.form.TransactionForm;
import com.paymybuddy.paymybuddy.services.interfaces.ITransaction;
import com.paymybuddy.paymybuddy.services.interfaces.IUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public String showTransactions(HttpSession session, Model model) {
        log.debug("transaction");
        User user = (User) session.getAttribute("user");

        List<TransactionDto> transactions = transactionService.getReceiverAndSenderTransaction(user);
        Set<String> emails = userService.getConnectionEmails(user);
        session.setAttribute("emails", emails);
        session.setAttribute("transactions", transactions);

        model.addAttribute("emails", emails);
        model.addAttribute("transactions", transactions);
        model.addAttribute("transactionForm", new TransactionForm());
        model.addAttribute("user", user);
        return "transaction";
    }

    @PostMapping
    public String createTransaction(@Validated @ModelAttribute TransactionForm transactionForm,
                              BindingResult result, HttpSession session, Model model){
        User userSession= (User) session.getAttribute("user");
        log.debug("POST transaction");
        Set<String> emails = (Set<String>) session.getAttribute("emails");
        List<TransactionDto> transactions = (List<TransactionDto>) session.getAttribute("transactions");


        if (result.hasErrors()) {
            model.addAttribute("emails", emails);
            model.addAttribute("transactions", transactions);
            model.addAttribute("error", result.getFieldError());
            return "transaction";
        }

        return "transaction";
    }
}
