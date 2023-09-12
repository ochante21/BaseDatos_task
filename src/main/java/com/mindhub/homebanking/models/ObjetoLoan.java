package com.mindhub.homebanking.models;

public class ObjetoLoan {
    private Long loanId;
    private Integer payments;
    private Double amount;
    private String toAccountNumber;

    public ObjetoLoan(Long loanId, Integer payments, Double amount, String toAccountNumber) {
        this.loanId = loanId;
        this.payments = payments;
        this.amount = amount;
        this.toAccountNumber = toAccountNumber;
    }

    public Long getLoanId() {
        return loanId;
    }

    public Integer getPayments() {
        return payments;
    }

    public Double getAmount() {
        return amount;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }
}
