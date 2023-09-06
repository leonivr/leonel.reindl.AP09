package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;

public class LoanApplicationDTO {

    private Long loanId;// id del préstamo
    private Double amount;
    private Integer payments;
    private String toAccountNumber;

    public LoanApplicationDTO() {
    }

    public LoanApplicationDTO(ClientLoan clientLoan, String toAccountNumber){

        this.loanId = clientLoan.getLoan().getId();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        //this.toAccountNumber = clientLoan.getClient().getAccounts().
        this.toAccountNumber = toAccountNumber;
    }

    public Long getLoanId() {
        return loanId;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }
}
