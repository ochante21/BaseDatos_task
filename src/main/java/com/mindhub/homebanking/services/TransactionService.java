package com.mindhub.homebanking.services;

import com.mindhub.homebanking.Enums.TransactionType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface TransactionService {
    ResponseEntity<Object> responseTransaction(
            Authentication authentication,
            //@RequestParam
            String fromAccountNumber,
            //@RequestParam
            String toAccountNumber,
            //@RequestParam
            Double amount,
            //@RequestParam
            String description);
}
