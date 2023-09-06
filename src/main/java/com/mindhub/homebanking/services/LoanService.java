package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.LoanDto;
import com.mindhub.homebanking.models.Loan;
import java.util.List;


public interface LoanService {
    List<LoanDto> getLoans();
    Loan findById(Long id);
    boolean existsById(Long id);
}
