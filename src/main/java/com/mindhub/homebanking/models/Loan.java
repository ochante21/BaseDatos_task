package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Loan {
    //atributos o propiedades
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String name;
    private Double maxAmount;

    @ElementCollection
    private List<Integer> payments = new ArrayList<>();

    //relacion muchos a muchos client-loan
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "loan")
    private Set<ClientLoan> clientLoans= new HashSet<>();

    //contructor

    public Loan(){}
    public Loan(String name, Double maxAmount, List<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
    }

    //metodos o comportamientos


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    //loan-clientLoan
    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }
    public void addClientLoan(ClientLoan clientLoan){
        clientLoan.setLoan(this);
        this.clientLoans.add(clientLoan);
    }
}
