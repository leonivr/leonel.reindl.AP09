package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import java.time.LocalDate;
public class CardDTO {
    private Long id;
    //private Client cardholder;
    //CARDHOLDERNAME
    private String cardHolder;
    private CardType type;
    private CardColor color;
    private String number;
    private int cvv;//VERIFICAR TIPO DE DATO
    private LocalDate fromDate;
    private LocalDate thruDate;
    public CardDTO(Card card){
        id = card.getId();
        type = card.getType();
        color = card.getColor();
        number = card.getNumber();
        cvv = card.getCvv();
        fromDate = card.getFromDate();
        thruDate = card.getThruDate();
        cardHolder = card.getCardHolderName();
    }

    public Long getId() {
        return id;
    }

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }

    public String getNumber() {
        return number;
    }

    public int getCvv() {
        return cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }
    //

    public String getCardHolder() {
        return cardHolder;
    }
}
