package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.CardDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CardService {
    List<CardDTO> getCardDTOS();

    ResponseEntity<Object> responseCard(
            //@RequestParam
            String cardType,
            //@RequestParam
            String cardColor,
            Authentication authentication);
}
