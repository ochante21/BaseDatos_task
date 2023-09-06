package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientService {

    List<ClientDTO> getClientsDTO();

    ClientDTO getClient(Long id);

    ResponseEntity<Object> register(

            //@RequestParam
            String firstName,
            //@RequestParam
            String lastName,
            //@RequestParam
            String email,
            //@RequestParam
            String password);

    ClientDTO getClientDTO(Authentication authentication);

}


