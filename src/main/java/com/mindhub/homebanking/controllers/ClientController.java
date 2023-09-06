package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.RoleType;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    //@RequestMapping(path = "/api/clients", method = RequestMethod.POST)
    @Autowired
    private AccountService accountService;
    @GetMapping("/clients/online")
    public ResponseEntity<Object> connection(Authentication authentication){
        if(authentication!=null){
            return new ResponseEntity<>("Conectado", HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>("No conectado",HttpStatus.FORBIDDEN);
        }
    }
    @GetMapping("/clients")
    public List<ClientDTO> getClients(){
        //map()
        /*return clientRepository.findAll()
                                .stream()
                                .map(client -> new ClientDTO(client))
                                .collect(Collectors.toList());*/
        return clientService.getClients();

    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id, Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());
        if(client == null){
            return new ResponseEntity("Client no encontrado",HttpStatus.BAD_GATEWAY);
        }
        if(client.getId() == id){
            return new ResponseEntity<>(new ClientDTO(client),HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity("Esta Cuenta no te pertenece",HttpStatus.I_AM_A_TEAPOT);
        }
    }
    //task6
    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (clientService.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }
        Client clientNew = new Client(firstName, lastName, email, passwordEncoder.encode(password),RoleType.CLIENT);
        Account account = accountService.createAccountInRegister();
        accountService.save(account);
        clientNew.addAccount(account);
        clientService.save(clientNew);
        return new ResponseEntity<>("Register", HttpStatus.ACCEPTED);
    }
    @GetMapping("/clients/current")
    public ClientDTO getCurrentClient(Authentication authentication) {
        return clientService.getCurrentClient(authentication);
    }//
}
