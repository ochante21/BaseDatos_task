package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        List<Account> listAccount = accountRepository.findAll();
        List<AccountDTO> accountDTO = listAccount.stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
        return accountDTO;
    }

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {

        Optional<Account> optionalAccount = accountRepository.findById(id);

        Account account = optionalAccount.orElse(null);

        AccountDTO accountDTO = new AccountDTO(account);

        return accountDTO;
    }


    //Agregar cuentas al cliente
    @RequestMapping(path = "/clients/current/accounts", method = RequestMethod.POST)

    public ResponseEntity<Object> responseAccount(Authentication authentication) {
        String number;
        LocalDate creationDate;
        Double balance;

        //cliente autenticado
        Client client = clientRepository.findByEmail(authentication.getName());


        if (client.getAccounts().size() >= 3) {

            return new ResponseEntity<>("No puedes tener mas de 3 cuentas", HttpStatus.FORBIDDEN);

        }

        Random random = new Random();
        Integer numeroRandom = random.nextInt(100000000);

        number = "VIN-" + numeroRandom.toString();

        creationDate = LocalDate.now();

        balance = 0.00;

        Account cuentaCreada = new Account(number, creationDate, balance);
        client.addAccount(cuentaCreada);

        accountRepository.save(cuentaCreada);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }


}
