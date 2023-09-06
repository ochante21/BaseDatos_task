package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AccountService {
    List<AccountDTO> getAccounts();

    AccountDTO getAccount(Long id);

    List<AccountDTO> getAccountDTO(Authentication authentication);

    ResponseEntity<Object> responseAccount(Authentication authentication);
}
