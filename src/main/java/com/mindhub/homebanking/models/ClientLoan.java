package com.mindhub.homebanking.models;

import com.sun.istack.NotNull;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ClientLoan {
    //atributos o propiedades
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private Integer payments;
    private Double amount;

    //tabla intermedia client-loan
    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;
    @ManyToOne(fetch = FetchType.EAGER)
    private Loan loan;

    //constructor
    public ClientLoan (){}

    public ClientLoan(Double amount ,Integer payment,String name) {
        this.payments = payment;
        this.amount=amount;
    }

    //metodos
    public long getId() {
        return id;
    }

    public Integer getPayment() {
        return payments;
    }

    public void setPayment(Integer payment) {
        this.payments = payment;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}
