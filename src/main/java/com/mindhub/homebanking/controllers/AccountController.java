package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("api")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;
    @GetMapping("/clients/current/accounts")//accounts
    public List<AccountDTO> getAccounts(Authentication authentication){
        Client client= clientService.findByEmail(authentication.getName());
        return client.getAccounts().stream().map(AccountDTO::new).collect(toList());
        //return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }
    /*@GetMapping("/accounts/{id}")
    public AccountDTO getAccountById(@PathVariable Long id){
        return new AccountDTO(accountRepository.findById(id).orElse(null));
    }*/

    @GetMapping("/accounts/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable Long id, Authentication authentication){
        Account account = accountService.findById(id);
        Client client = clientService.findByEmail(authentication.getName());
        if(account == null){
            return new ResponseEntity("Cuenta no encontrada",HttpStatus.BAD_GATEWAY);
        }
        if (account.getClient().equals(client)){
            AccountDTO accountDTO = new AccountDTO(account);
            return new ResponseEntity<>(accountDTO, HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity("Esta cuenta no es tuya",HttpStatus.I_AM_A_TEAPOT);
        }
    }

    /*@GetMapping("/clients/current/accounts")
    public List<AccountDTO> getAccounts(){
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }*/
    @PostMapping("/clients/current/accounts")//Crear Cuenta
    public ResponseEntity<Object> createAccount(Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());
        if (client.getAccounts().size()<3) {
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
            } while (accountService.existsByNumber(randomAccount));
            System.out.println("Cliente:" + client.getFirstName() + " " + client.getLastName());
            System.out.println("Nueva Cuenta:" + randomAccount);
            Account currentAccount = new Account(randomAccount, LocalDateTime.now(), 0);
            accountService.save(currentAccount);
            client.addAccount(currentAccount);
            clientService.save(client);
            return new ResponseEntity<>("Created", HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("El cliente ya posee el máximo de cuentas permitidas",HttpStatus.FORBIDDEN);
        }
    }
}