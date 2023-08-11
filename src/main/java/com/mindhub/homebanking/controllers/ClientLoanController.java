package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
public class ClientLoanController {
    @Autowired
    private ClientLoanRepository clientLoanRepository;
    @GetMapping("/clientLoans")
    public List<ClientLoanDTO> getLoans(){
        return clientLoanRepository.findAll().stream().map(ClientLoanDTO::new).collect(Collectors.toList());
    }
    @GetMapping("/clientLoans/{id}")
    public ClientLoanDTO getLoanById(@PathVariable Long id){
        return new ClientLoanDTO(clientLoanRepository.findById(id).orElse(null));
    }
}
