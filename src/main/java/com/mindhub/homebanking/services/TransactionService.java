package com.mindhub.homebanking.services;

import com.mindhub.homebanking.Enums.TransactionType;
import com.mindhub.homebanking.models.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface TransactionService {
    void saveTransaction(Transaction transaction);
}
