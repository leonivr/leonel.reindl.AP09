package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccountServiceImplement implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public Account findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public boolean existsByNumber(String number) {
        return accountRepository.existsByNumber(number);
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account createAccountInRegister() {
        int random;
        String randomAccount;
        do {
            random = (int) ((Math.random() * (99999999 - 5)) + 5);
            if (Integer.toString(random).length()==1){
                randomAccount = "VIN00" + random;
            } else if (Integer.toString(random).length()==2) {
                randomAccount = "VIN0" + random;
            }else {
                randomAccount = "VIN" + random;
            }
        } while (accountRepository.existsByNumber(randomAccount));
        Account currentAccount = new Account(randomAccount, LocalDateTime.now(), 0);
        return currentAccount;
    }
}
