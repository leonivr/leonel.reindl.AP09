package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Account;

public interface AccountService {
    Account findByNumber(String number);
    void save(Account account);
    boolean existsByNumber(String number);
    Account findById(Long id);
    Account createAccountInRegister();
}
