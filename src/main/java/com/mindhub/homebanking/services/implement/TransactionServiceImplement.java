package com.mindhub.homebanking.services.implement;

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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class TransactionServiceImplement implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public ResponseEntity<Object> responseTransaction(
            Authentication authentication,
            //@RequestParam
            String fromAccountNumber,
            //@RequestParam
            String toAccountNumber,
            //@RequestParam
            Double amount,
            //@RequestParam
            String description) {
        //verificar
        if(fromAccountNumber.equals(toAccountNumber)){
            return new ResponseEntity<>("No se puede transferir ala misma cuenta", HttpStatus.FORBIDDEN);
        }
        if (fromAccountNumber.isBlank()||toAccountNumber.isBlank()||amount==0||description.isBlank()){
            return new ResponseEntity<>("Rellene los espacios en blanco", HttpStatus.FORBIDDEN);

        }
        //obtener al cliente autentificado
        Client client = clientRepository.findByEmail(authentication.getName());

        //obtener la cuenta del cliente
        Set<Account> accountsSet = client.getAccounts();
        Account accountOrigin = accountsSet.stream().filter(account -> account.getNumber().equals(fromAccountNumber)).findFirst().orElse(null);
        if (accountOrigin == null) {
            return new ResponseEntity<>("La cuenta origen no pertenece al cliente autentificado", HttpStatus.FORBIDDEN);
        }

        //obtener la cuenta del cliente destino
        Account accountDestiny = accountRepository.findByNumber(toAccountNumber);
        if (accountDestiny == null) {
            return new ResponseEntity<>("No existe la cuenta destino", HttpStatus.FORBIDDEN);
        }

        if (accountOrigin.getBalance()<amount){
            return new ResponseEntity<>("No tienes fondos suficientes", HttpStatus.FORBIDDEN);
        }

        //creo la transaccion
        Transaction transaccionOrigen= new Transaction(TransactionType.DEBIT,amount,description, LocalDateTime.now());
        Transaction transaccionDestino= new Transaction(TransactionType.CREDIT,amount,description,LocalDateTime.now());

        //agrergar y guardar las transacciones
        accountOrigin.setBalance(accountOrigin.getBalance()-amount);
        accountDestiny.setBalance(accountDestiny.getBalance()+amount);

        accountOrigin.addTransaction(transaccionOrigen);
        accountDestiny.addTransaction(transaccionDestino);

        transactionRepository.save(transaccionOrigen);
        transactionRepository.save(transaccionDestino);
        accountRepository.save(accountOrigin);
        accountRepository.save(accountDestiny);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
