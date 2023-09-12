package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Enums.TransactionType;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.dtos.LoansDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.ClientLoanService;
import com.mindhub.homebanking.services.LoansService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoansController {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientLoanService clientLoanService;
    @Autowired
    private LoansService loansService;
    @Autowired
    private TransactionService transactionService;

    @RequestMapping("/loans")
    public List<LoansDTO> getLoansDTO() {
        return loansService.getLoansDTO();
    }

    @Transactional
    @RequestMapping(path = "loans", method = RequestMethod.POST)
    public ResponseEntity<String> addLoan(@RequestBody ObjetoLoan clientLoan, Authentication authentication) {
        Double amount = clientLoan.getAmount();
        Integer payments = clientLoan.getPayments();
        Long loanID = clientLoan.getLoanId();
        String toAccountNumber = clientLoan.getToAccountNumber();

        //cliente autenticado
        Client client = clientRepository.findByEmail(authentication.getName());

        if (amount == 0 || payments == 0 || toAccountNumber.isBlank()) {
            return new ResponseEntity<>("Complete los espacion vacios", HttpStatus.FORBIDDEN);
        }

        //tipo de credito solicitado
        Loan loan = loanRepository.findById(loanID).orElse(null);
        if (loan == null) {
            return new ResponseEntity<>("No existe el tipo de prestamo", HttpStatus.FORBIDDEN);
        }

        if (amount > loan.getMaxAmount()) {
            return new ResponseEntity<>("El monto solicitado excede al monto de prestamo maximo ", HttpStatus.FORBIDDEN);
        }

        if (!loan.getPayments().contains(payments)) {
            return new ResponseEntity<>("Las cantidad de cuotas no coincide con el tipo de prestamo", HttpStatus.FORBIDDEN);
        }

        //transferir el prestamo a la cuenta solicitada
        //obtener la cuenta del cliente
        Set<Account> accountsSet = client.getAccounts();
        Account accountClient = accountsSet.stream().filter(account -> account.getNumber().equals(toAccountNumber)).findFirst().orElse(null);
        if (accountClient == null) {
            return new ResponseEntity<>("La cuenta origen no pertenece al cliente autentificado o no existe", HttpStatus.FORBIDDEN);
        }
        Transaction transaccionDestino = new Transaction(TransactionType.CREDIT, amount, "prestamo", LocalDateTime.now());
        //agrergar y guardar las transacciones
        accountClient.addTransaction(transaccionDestino);

        accountClient.setBalance(accountClient.getBalance() + amount);
        transactionService.saveTransaction(transaccionDestino);


        //crear prestamo solicitado
        ClientLoan clientLoan1 = new ClientLoan(amount + amount * 20 / 100, payments, loan.getName());

        //agregar al cliente la cantidad y el tipo de prestamos
        client.addClientLoan(clientLoan1);
        loan.addClientLoan(clientLoan1);

        clientLoanService.saveClientLoan(clientLoan1);


        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
