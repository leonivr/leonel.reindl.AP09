package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn( name = "client_id")
    private Client cardholder;
    private CardType type;
    private CardColor color;
    private String number;
    private int cvv;
    private LocalDateTime fromDate;
    private LocalDateTime thruDate;
    public Card(){}
    public Card(Client cardholder, CardType type, CardColor color, String number, int cvv, LocalDateTime fromDate, LocalDateTime thruDate) {
        this.cardholder = cardholder;
        this.type = type;
        this.color = color;
        this.number = number;
        this.cvv = cvv;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
    }

    public Long getId() {
        return id;
    }

    public Client getCardholder() {
        return cardholder;
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

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public LocalDateTime getThruDate() {
        return thruDate;
    }

    public void setCardholder(Client cardholder) {
        this.cardholder = cardholder;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public void setThruDate(LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

    //get cardHolderName
    public String getCardHolderName(){
        return this.cardholder.getFirstName()+" "+this.cardholder.getLastName();
    }

}
