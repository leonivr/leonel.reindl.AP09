package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;
public class ClientLoanDTO {
    private Long id;
    private Long loanId;// id del préstamo
    private String name; //Nombre del préstamo
    private Double amount;
    private Integer payments;
    public ClientLoanDTO(ClientLoan clientLoan){
        id=clientLoan.getId();
        loanId = clientLoan.getLoan().getId();//
        name = clientLoan.getLoan().getName();//
        amount = clientLoan.getAmount();
        payments = clientLoan.getPayments();
    }
    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public Long getLoanId() {
        return loanId;
    }

    public String getName() {
        return name;
    } //Importante: "Los nombres de las funciones determinan como se muestran los campos en la web"
}
