package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

public class ClientLoanDTO {
    private long id;
    private String name;
    private Double amount;
    private Integer payments;
    private Long loanId;


    public ClientLoanDTO(ClientLoan clientLoan) {
        id = clientLoan.getId();
        payments = clientLoan.getPayment();
        amount = clientLoan.getAmount();
        name=clientLoan.getLoan().getName();
        loanId= clientLoan.getLoan().getId();
    }

    public long getId() {
        return id;
    }

    public Long getLoanId() {
        return loanId;
    }

    public String getName() {
        return name;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }
}
