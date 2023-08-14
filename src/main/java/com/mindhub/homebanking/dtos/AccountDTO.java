package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private Long id;
    private String number;
    private LocalDate creationDate;
    private Double balance;

    //uno a muchos account-transaction
    private Set<TransactionDTO> transactions;

    public AccountDTO(Account account){
        id=account.getId();
        number= account.getNumber();
        creationDate=account.getCreationDate();
        balance= account.getBalance();
        transactions=account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());

    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Double getBalance() {
        return balance;
    }

    //account-transaction
    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }
}
