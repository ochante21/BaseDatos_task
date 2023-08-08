package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;
    @RequestMapping("clients")
    public List<ClientDTO> getClients(){

        List<Client> listClient= clientRepository.findAll();
        List<ClientDTO> listClientDTO= listClient.stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
        return listClientDTO;
    }

    @RequestMapping("clients/{id}")
    public ClientDTO getClient(@PathVariable long id){
        Optional<Client> optionalClient= clientRepository.findById(id);

        Client client = optionalClient.orElse(null);

        ClientDTO clientDTO= new ClientDTO(client);

        return clientDTO;
    }

}
