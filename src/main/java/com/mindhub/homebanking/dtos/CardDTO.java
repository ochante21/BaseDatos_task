package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.Enums.CardColor;
import com.mindhub.homebanking.Enums.CardType;
import com.mindhub.homebanking.models.Card;

import java.time.LocalDate;
import java.util.Set;

public class CardDTO {
    private Long id;
    private String cardHolder;
    private CardType type;
    private CardColor color;
    private Long number;
    private Integer cvv;
    private LocalDate fromDate;
    private LocalDate thruDate;

    //muchos a muchos client card
    //private Set<ClientDTO> cards;

    public CardDTO(Card card){
        id= card.getId();
        cardHolder=card.getCardHolder();
        type=card.getType();
        color=card.getColor();
        number=card.getNumber();
        cvv=card.getCvv();
        fromDate=card.getFromDate();
        thruDate=card.getThruDate();

    }

    public Long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }

    public Long getNumber() {
        return number;
    }

    public Integer getCvv() {
        return cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

}
