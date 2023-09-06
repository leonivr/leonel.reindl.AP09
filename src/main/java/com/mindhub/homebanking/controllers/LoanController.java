package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDto;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
public class LoanController {
    @Autowired
    private LoanService loanService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientLoanService clientLoanService;
    @Autowired
    private TransactionService transactionService;
    @GetMapping("/loans")
    public List<LoanDto> getLoans(){
        return loanService.getLoans();
    }
    /*@GetMapping("/loans/{id}")
    public LoanDto getLoanById(@PathVariable Long id){
        return new LoanDto(loanRepository.findById(id).orElse(null));
    }*/

    //task9
    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> applyForLoan(@RequestBody LoanApplicationDTO applyLoan , Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());
        Set<Account> accounts = client.getAccounts();
        Loan loan = loanService.findById(applyLoan.getLoanId());//si LoanId = 0 -> loan == null
        Account account = accountService.findByNumber(applyLoan.getToAccountNumber());
        if(loan == null){//objeto no vacío
            return new ResponseEntity<>("Loan Not Found",HttpStatus.FORBIDDEN);
        }
        if(loan.getName().isEmpty() || loan.getPayments().isEmpty() || applyLoan.getToAccountNumber().isEmpty() || applyLoan.getLoanId()==0){
            return new ResponseEntity<>("Missing Data",HttpStatus.FORBIDDEN);
        }
        //loanRepository.findById(loanId)!=null;
        if(!loanService.existsById(applyLoan.getLoanId())){//préstamo exista
            return new ResponseEntity<>("Loan Not Found",HttpStatus.FORBIDDEN);
        }
        //monto Solicitado <= prestamo.maxAccount;
        if(applyLoan.getAmount()<=0 || loan.getMaxAmount()< applyLoan.getAmount() || applyLoan.getAmount().isNaN()){
            return new ResponseEntity<>("you must indicate a correct amount",HttpStatus.FORBIDDEN);
        }
        //cantidad de cuotas se encuentre en las disponibles
        if(!loan.getPayments().contains(applyLoan.getPayments())){//boolean exists payments
            return new ResponseEntity<>("you must indicate a correct payments",HttpStatus.FORBIDDEN);
        }
        //cuenta destino exista y sea del cliente autenticado
        if(!accounts.contains(account)){//toAccountNumber
            return new ResponseEntity<>("Cuenta Destinatario no existe", HttpStatus.FORBIDDEN);
        }
        //Tipo de Préstamo Ya Solicitado
        Set<ClientLoan> loanForTypes = client.getLoans().stream().filter(loanType -> loanType.getLoan().getName().equals(loan.getName())).collect(Collectors.toSet());
        if(!loanForTypes.isEmpty()){
            return new ResponseEntity<>("Tipo préstamo ya fue solicitado",HttpStatus.FORBIDDEN);
        }
        ClientLoan clientLoan = new ClientLoan(applyLoan.getAmount(), applyLoan.getPayments());
        client.addClientLoan(clientLoan);
        loan.addClientLoan(clientLoan);
        clientLoanService.save(clientLoan);
        Transaction transaction = new Transaction(TransactionType.CREDIT, applyLoan.getAmount()*(1.2),loan.getName()+" loan approved", LocalDateTime.now());
        transactionService.save(transaction);
        account.addTransaction(transaction);
        double newBalance = account.getBalance()+applyLoan.getAmount()*(1.2);
        account.setBalance(newBalance);

        for (ClientLoan clientLoan1: client.getLoans()) {
            System.out.println(clientLoan1.getLoan().getId());
            System.out.println("Name:"+clientLoan1.getLoan().getName());
            System.out.println("Payments:"+clientLoan1.getPayments());
            System.out.println("Amount:"+clientLoan1.getAmount());

        }
        return new ResponseEntity<>("loan approved", HttpStatus.CREATED);
    }
}