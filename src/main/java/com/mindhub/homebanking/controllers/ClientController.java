package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/clients")
    public List<ClientDTO> getClients(){
        //función map()
        /*return clientRepository.findAll()
                                .stream()
                                .map(client -> new ClientDTO(client))
                                .collect(Collectors.toList());*/
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());

    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClientById(@PathVariable Long id){
        /*Optional<Client> clientOptional = clientRepository.findById(id);
        return new ClientDTO(clientOptional.get());*/
        return new ClientDTO(clientRepository.findById(id).orElse(null));
    }
}
