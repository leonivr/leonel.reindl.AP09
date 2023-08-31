package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
public class TransactionController {
    @Autowired
    private TransactionRepository transactionRepository;
    /*@GetMapping("/transactions")
    public List<TransactionDTO> getTransactions(){
        return transactionRepository.findAll().stream().map(TransactionDTO::new).collect(Collectors.toList());
    }

    @GetMapping("transactions/{id}")
    public TransactionDTO getTransactionById(@PathVariable Long id){
        return new TransactionDTO(transactionRepository.findById(id).orElse(null));
    }*/

    //-----Task 8-----
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Transactional
    @PostMapping("transactions")//transfers.js MIRAR los NOMBRES de los PARÁMETROS,
    public ResponseEntity<Object> createTransaction(@RequestParam double amount,@RequestParam String description,@RequestParam String fromAccountNumber,@RequestParam String toAccountNumber,Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        Set<String> accountsNumbers = client.getAccounts().stream().map(Account::getNumber).collect(Collectors.toSet());
        Account originAccount = accountRepository.findByNumber(fromAccountNumber);
        Account destinationAccount = accountRepository.findByNumber(toAccountNumber);
        if(amount==0 || description.isBlank() || fromAccountNumber.isEmpty() ||toAccountNumber.isBlank()){
            return new ResponseEntity<>("Missing Data",HttpStatus.BAD_REQUEST);
        }
        if(!accountsNumbers.contains(fromAccountNumber)){
            return new ResponseEntity<>("Origin account does not exist",HttpStatus.BAD_REQUEST);
        }
        if(!accountRepository.existsByNumber(toAccountNumber)){
            return new ResponseEntity<>("Destination account does not exist",HttpStatus.BAD_REQUEST);
        }
       if(amount < 0 || originAccount.getBalance()<amount){
           return new ResponseEntity<>("You can't transfer that amount",HttpStatus.BAD_REQUEST);
       }
       if(fromAccountNumber.equals(toAccountNumber)){
           return new ResponseEntity<>("Origin and destination accounts cannot be the same",HttpStatus.FORBIDDEN);
       }
        double newOriginBalance = originAccount.getBalance()-amount;
        double newDestinationBalance = destinationAccount.getBalance()+amount;
        originAccount.setBalance(newOriginBalance);
        destinationAccount.setBalance(newDestinationBalance);
        Transaction debitTransaction = new Transaction(TransactionType.DEBIT,-amount,description+" "+fromAccountNumber, LocalDateTime.now());
        originAccount.addTransaction(debitTransaction);
        transactionRepository.save(debitTransaction);
        Transaction creditTransaction = new Transaction(TransactionType.CREDIT,amount,description+" "+toAccountNumber, LocalDateTime.now());
        destinationAccount.addTransaction(creditTransaction);
        transactionRepository.save(creditTransaction);
        return new ResponseEntity<>(authentication.getName(),HttpStatus.ACCEPTED);
    }
}
