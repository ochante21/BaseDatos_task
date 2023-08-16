package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import org.aspectj.asm.IElementHandleProvider;

import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;

    //uno a muchos client-account
    private Set<AccountDTO>    accounts;

    //uno a muchos client - clientloan
    private Set<ClientLoanDTO> loans;

    //muchos a muchos client-card
    private  Set<CardDTO> cards;

    public ClientDTO(Client client) {
        id=client.getId();
        firstName = client.getFirstName();
        lastName = client.getLastName();
        email= client.getEmail();
        accounts=client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
        loans=client.getClientLoans().stream().map(clientLoan -> new ClientLoanDTO(clientLoan)).collect(Collectors.toSet());
        cards=client.getCards().stream().map(card -> new CardDTO(card)).collect(Collectors.toSet());
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    //client-account
    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    //client-clientloan
    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }

    //client-card
    public Set<CardDTO> getCards() {
        return cards;
    }
}
