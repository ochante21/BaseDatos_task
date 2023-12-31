package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Client {
    //atributos o propiedades
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String firstName;
    private String lastName;
    private String email,password;

    //relacion uno a muchos client-account
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "client")
    private Set<Account> accounts= new HashSet<>();

    //relacion muchos a muchos client-loan (con tabla intermedia)
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "client")
    private Set<ClientLoan> clientLoans= new HashSet<>();

    //relacion muchos a muchos client-card(tabla intermedia generada automaticamente)
    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "clients")
    private Set<Card> cards= new HashSet<>();

    //constructores
    public Client() { }

    public Client(String first, String last, String mail, String password) {
        this.firstName = first;
        this.lastName = last;
        this.email= mail;
        this.password= password;
    }
    //metodos o comportamientos

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String toString() {
        return firstName + " " + lastName;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //client-account
    public Set<Account> getAccounts() {
        return accounts;
    }
    public void addAccount(Account acount){
        acount.setClient(this);
        this.accounts.add(acount);
    }

    //client-clientLoan
    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }
    public void addClientLoan(ClientLoan clientLoan){
        clientLoan.setClient(this);
        this.clientLoans.add(clientLoan);
    }

    //client-card

    public Set<Card> getCards() {
        return cards;
    }
    public void addCard(Card card){
        card.getClients().add(this);
        cards.add(card);
    }
}
