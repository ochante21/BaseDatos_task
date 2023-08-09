package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts (){
        List<Account> listAccount= accountRepository.findAll();
        List<AccountDTO> accountDTO=listAccount.stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
        return accountDTO;
    }

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){

        Optional<Account> optionalAccount= accountRepository.findById(id);

        Account account = optionalAccount.orElse(null);

        AccountDTO accountDTO= new AccountDTO(account);

        return accountDTO;
    }



}
