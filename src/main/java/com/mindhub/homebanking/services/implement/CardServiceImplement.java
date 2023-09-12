package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImplement implements CardService {
    @Autowired
    private CardRepository cardRepository;
    @Override
    public boolean existsByNumber(String number) {
        return cardRepository.existsByNumber(number);
    }
    @Override
    public void save(Card card) {
        cardRepository.save(card);
    }

    /*@Override //IMPLEMENT EN CardUtils
    public String getCardNumber() {
        String cardNumber ="";
        for(int i=0;i<4;i++){
            int num = (int) ((Math.random() * (9999 - 1000)) + 1000);
            if(i!=3){
                cardNumber += num + "-";
            }else {
                cardNumber += num;
            }
        }
        return cardNumber;
    }*/
}
