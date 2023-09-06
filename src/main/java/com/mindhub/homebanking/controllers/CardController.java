package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("api")
public class CardController {
    @Autowired
    private CardService cardService;
    @Autowired
    private ClientService clientService;

    @GetMapping("/clients/current/cards")//
    public List<CardDTO> getCards(Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());
        return client.getCards().stream().map(CardDTO::new).collect(toList());
    }
    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardColor cardColor, @RequestParam CardType cardType, Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());
        Set<Card> cards = client.getCards();
        if(!cards.stream().filter(card -> card.getType().equals(cardType)).filter(card -> card.getColor().equals(cardColor)).collect(Collectors.toSet()).isEmpty()){
            return new ResponseEntity<>("Card already exists",HttpStatus.FORBIDDEN);
        }
        String random;
        int cvv = (int) ((Math.random() * (999 - 100)) + 100);
        do{
            random = cardService.cardNumberGenerator();
        }while (cardService.existsByNumber(random));
        Card card = new Card(client,cardType,cardColor,random,cvv, LocalDateTime.now(), LocalDateTime.now().plusYears(5));
        cardService.save(card);
        client.addCard(card);
        clientService.save(client);
        return new ResponseEntity<>("created", HttpStatus.CREATED);
        /*leo-List<CardType> types = client.getCards().stream().map(Card::getType).collect(Collectors.toList());
        int credit = 0;
        int debit = 0;
        for (CardType type: types ){
            if(type.equals(CardType.CREDIT)){
              credit++;
            }else{
              debit++;
            }
        }
        boolean flag = false;
        for (Card card:client.getCards()
             ) {
            if (card.getType().equals(cardType) && card.getColor().equals(cardColor)){
                flag = true;
            }
        }
        if((cardType.equals(CardType.CREDIT) && credit < 3 && !flag) || (cardType.equals(CardType.DEBIT) && debit < 3 && !flag)){
            String random;
            int cvv = (int) ((Math.random() * (999 - 100)) + 100);
            do{
                random = Card.cardNumberGenerator();//method static en Card
            }while (cardRepository.existsByNumber(random));
            Card card = new Card(client,cardType,cardColor,random,cvv, LocalDate.now(),LocalDate.now().plusYears(5));
            cardRepository.save(card);
            client.addCard(card);
            clientRepository.save(client);
            if(cardType.equals(CardType.CREDIT)){
                credit++;
            }else{
                debit++;
            }
            System.out.println("Credit:"+ credit);
            System.out.println("Debit:"+ debit);
            return new ResponseEntity<>("created", HttpStatus.CREATED);
        }else{
            return  new ResponseEntity<>("Reached the maximum number of cards allowed",HttpStatus.FORBIDDEN);
        }-leo*/
    }
}