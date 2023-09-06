package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.models.ClientLoan;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ClientLoanService {
    void save(ClientLoan clientLoan);
    List<ClientLoanDTO> getLoans();
    ClientLoanDTO getLoanById(Long id);

}
