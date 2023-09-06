package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Enums.TransactionType;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Transactional
    @RequestMapping(path = ("/transactions"), method = RequestMethod.POST)
    public ResponseEntity<Object> responseTransaction(
            Authentication authentication,
            @RequestParam
            String fromAccountNumber,
            @RequestParam
            String toAccountNumber,
            @RequestParam
            Double amount,
            @RequestParam
            String description) {
        return transactionService.responseTransaction(authentication,fromAccountNumber,toAccountNumber,amount,description);
    }


}
