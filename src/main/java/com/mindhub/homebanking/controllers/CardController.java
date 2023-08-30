package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Enums.CardColor;
import com.mindhub.homebanking.Enums.CardType;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/cards")
    public List<CardDTO> getCardDTOS() {

        List<Card> listCardDTO = cardRepository.findAll();
        List<CardDTO> cardDTOList = listCardDTO.stream().map(card -> new CardDTO(card)).collect(Collectors.toList());
        return cardDTOList;
    }

    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> responseCard(
            @RequestParam
            String cardType,
            @RequestParam
            String cardColor,
            Authentication authentication) {
        CardType type;
        CardColor color;
        //obtener cliente autenticado
        Client client = clientRepository.findByEmail(authentication.getName());

        //traer las tarjetas del cliente
        Set<Card> cardSet = client.getCards();
        List<CardType> typeList = cardSet.stream().map(card -> card.getType()).collect(Collectors.toList());

        System.out.println(typeList);

        int countDebit = 0;
        int countCredit = 0;
        for (CardType cardTipo : typeList) {
            if (cardTipo == CardType.DEBIT) {
                countDebit++;
                System.out.println(countDebit);
            } else if (cardTipo == CardType.CREDIT) {
                countCredit++;
                System.out.println(countCredit);
            }
        }

        if (countDebit < 3 & cardType.equals("DEBIT")) {
            type = CardType.DEBIT;
        } else if (countCredit < 3 & cardType.equals("CREDIT")) {
            type = CardType.CREDIT;
        } else {
            return new ResponseEntity<>("No puede tener mas tarjetas  tipo " + cardType, HttpStatus.FORBIDDEN);

        }

        switch (cardColor) {
            case "SILVER":
                color = CardColor.SILVER;
                break;
            case "GOLD":
                color = CardColor.GOLD;
                break;
            case "TITANIUM":
                color = CardColor.TITANIUM;
                break;
            default:
                return new ResponseEntity<>("seleccione un COLOR de tarjeta valido", HttpStatus.FORBIDDEN);
        }

        Random random = new Random();
        Integer numeroRandom = random.nextInt(1000);


        Integer cvv = numeroRandom;
        String number = random.nextInt(10000) + "-" + random.nextInt(10000) + "-" + random.nextInt(10000) + "-" + random.nextInt(10000);
        String cardHolder = client.toString();

        Card nuevaTarjeta = new Card(cardHolder, type, color, number, cvv, LocalDate.now(), LocalDate.now().plusYears(2));

        client.addCard(nuevaTarjeta);
        cardRepository.save(nuevaTarjeta);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
