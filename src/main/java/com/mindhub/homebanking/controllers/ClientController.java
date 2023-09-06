package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.configuration.WebAuthentication;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @RequestMapping("clients")
    public List<ClientDTO> getClients() {
        return clientService.getClientsDTO();
    }

    @RequestMapping("clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return clientService.getClient(id);
    }

    //registar un cliente
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
        return clientService.register(firstName, lastName, email, password);
    }

    //Retorna informacion del cliente autentificado
    @RequestMapping("/clients/current")
    public ClientDTO getClientDTO(Authentication authentication) {
        return clientService.getClientDTO(authentication);
    }

}


