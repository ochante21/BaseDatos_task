package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.configuration.WebAuthentication;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

    //registar un cliente
    @Autowired
    private PasswordEncoder passwordEncoder;
    @RequestMapping(path = "clients", method = RequestMethod.POST)

    public ResponseEntity<Object> register(

            @RequestParam
            String firstName,
            @RequestParam
            String lastName,

            @RequestParam
            String email,
            @RequestParam
            String password) {



        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {

            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

        }



        if (clientRepository.findByEmail(email) !=  null) {

            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);

        }



        clientRepository.save(new Client(firstName, lastName, email, passwordEncoder.encode(password)));

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    //Retorna informacion del cliente autentificado
    @RequestMapping("/clients/current")

    public ClientDTO getClientDTO(Authentication authentication) {

        Client client=  clientRepository.findByEmail(authentication.getName());
        ClientDTO clientDTO= new ClientDTO(client);
        return clientDTO;

    }

    //@RequestMapping()

}


