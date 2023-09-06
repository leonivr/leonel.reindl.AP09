package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ClientService {
    Client findByEmail(String email);
    List<ClientDTO> getClients();
    ClientDTO getClientById(Long id);
    void save(Client client);
    ClientDTO getCurrentClient(Authentication authentication);
}
