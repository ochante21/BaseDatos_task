package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplement implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<AccountDTO> getAccounts() {
        List<Account> listAccount = accountRepository.findAll();
        List<AccountDTO> accountDTO = listAccount.stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
        return accountDTO;
    }

    @Override
    public AccountDTO getAccount(Long id) {

        Optional<Account> optionalAccount = accountRepository.findById(id);

        Account account = optionalAccount.orElse(null);

        AccountDTO accountDTO = new AccountDTO(account);

        return accountDTO;
    }

    @Override
    public List<AccountDTO> getAccountDTO(Authentication authentication) {

        Client client = clientRepository.findByEmail(authentication.getName());
        Set<Account> accountsSet = client.getAccounts();
        List<AccountDTO> accountDTO = accountsSet.stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
        return accountDTO;

    }

    //guardarcuenta
    @Override
    public void saveAccount(Account account){
        accountRepository.save(account);
    }



}
