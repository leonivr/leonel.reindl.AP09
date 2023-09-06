package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Loan;
import javax.persistence.ElementCollection;
import java.util.Set;
import java.util.stream.Collectors;

public class LoanDto {
    private Long id;
    private String name;
    private double maxAmount;
    @ElementCollection
    private Set<Integer> payments;
    //private Set<ClientLoanDTO> loans;

    public LoanDto(Loan loan) {
        id = loan.getId();
        name = loan.getName();
        maxAmount = loan.getMaxAmount();
        payments = loan.getPayments();
        //loans = loan.getClientLoans().stream().map(ClientLoanDTO::new).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public Set<Integer> getPayments() {
        return payments;
    }

    /*public Set<ClientLoanDTO> getClientLoans() {
        return loans;
    }*/
}
