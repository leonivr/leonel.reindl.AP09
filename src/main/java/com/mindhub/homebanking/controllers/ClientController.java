package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.RoleType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/clients")
    public List<ClientDTO> getClients(){
        //map()
        /*return clientRepository.findAll()
                                .stream()
                                .map(client -> new ClientDTO(client))
                                .collect(Collectors.toList());*/
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());

    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClientById(@PathVariable Long id){
        return new ClientDTO(clientRepository.findById(id).orElse(null));
    }
    //task6
    @Autowired
    private PasswordEncoder passwordEncoder;
    //@RequestMapping(path = "/api/clients", method = RequestMethod.POST)

    @Autowired
    private AccountRepository accountRepository;
    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (clientRepository.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }
        Client clienteNuevo = new Client(firstName, lastName, email, passwordEncoder.encode(password),RoleType.CLIENT);
        Account account = createAccountInRegister();
        accountRepository.save(account);
        clienteNuevo.addAccount(account);
        clientRepository.save(clienteNuevo);
        return new ResponseEntity<>("Register", HttpStatus.ACCEPTED);
    }
    @GetMapping("/clients/current")
    public ClientDTO getCurrentClient(Authentication authentication) {
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }//
    public Account createAccountInRegister(){
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
