package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.entities.Transaction;
import com.paymybuddy.paymybuddy.services.interfaces.ITransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private ITransaction transactionService;

    @GetMapping
    public String transaction(Model model) {
        List<Transaction> transactions = transactionService.getAllTransaction();

        model.addAttribute("transaction", transactions);
        return "transaction";
    }

}
