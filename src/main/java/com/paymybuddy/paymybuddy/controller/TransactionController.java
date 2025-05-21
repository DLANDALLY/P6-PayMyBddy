package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dtos.transaction.TransactionDto;
import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.form.TransactionForm;
import com.paymybuddy.paymybuddy.services.interfaces.ITransaction;
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
import java.util.Set;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {
    private ITransaction transactionService;
    private IUser userService;

    @GetMapping
    public String showTransactions(HttpSession session, Model model) {
        log.info("GET Transaction");
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        try {
            List<TransactionDto> transactions = transactionService.getAllUserTransactions(user);
            Set<String> emails = userService.getConnectionEmails(user.getId());
            session.setAttribute("emails", emails);
            session.setAttribute("transactions", transactions);

            model.addAttribute("emails", emails);
            model.addAttribute("transactions", transactions);
            model.addAttribute("transactionForm", new TransactionForm());
            model.addAttribute("user", user);
            model.addAttribute("maxBalance", user.getBankAccount().getBalance());
            return "transaction";
        }catch (Exception e){
            model.addAttribute("errorMessage", e.getMessage());
            return "transaction";
        }
    }

    @PostMapping
    public String submitTransaction(@Valid @ModelAttribute TransactionForm transactionForm,
                              BindingResult result, HttpSession session, Model model){
        User userSession= (User) session.getAttribute("user");
        if (userSession == null) return "redirect:/login";

        log.debug("POST transaction");
        Set<String> emails = (Set<String>) session.getAttribute("emails");
        List<TransactionDto> transactions = (List<TransactionDto>) session.getAttribute("transactions");

        if (result.hasErrors()) {
            model.addAttribute("emails", emails);
            model.addAttribute("transactions", transactions);
            model.addAttribute("errors", result.getAllErrors());
            model.addAttribute("maxBalance", userSession.getBankAccount().getBalance());
            return "transaction";
        }

        try{
            transactionService.processTransaction(userSession, transactionForm);
        }catch (RuntimeException re){
            model.addAttribute("emails", emails);
            model.addAttribute("transactions", transactions);
            model.addAttribute("errorMessage", re.getMessage());
            model.addAttribute("maxBalance", userSession.getBankAccount().getBalance());
            return "transaction";
        }
        return "redirect:/transaction";
    }
}
