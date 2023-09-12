package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.stream.Collectors;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class CardUtilsTests {
    @Autowired
    CardRepository cardRepository;
    @Test
    public void cardNumberIsCreated(){
        String cardNumber = CardUtils.getCardNumber();
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }

    //getCardNumber
    @Test
    public void cardNumberAlreadyExists(){
        String cardNumber = CardUtils.getCardNumber();
        List<String> cardNumbers = cardRepository.findAll().stream().map(Card::getNumber).collect(Collectors.toList());
        assertThat(cardNumber,not(cardNumbers.contains(cardNumber)));
    }

    //getCvv
    @Test
    public void cVvIsCreated(){
        String cvv = String.valueOf(CardUtils.getCvv());
        assertThat(cvv,is(not(emptyOrNullString())));
    }
    @Test
    public void cVvAlreadyExists(){
        int cvv = CardUtils.getCvv();
        List<Integer> cvvs =cardRepository.findAll().stream().map(Card::getCvv).collect(Collectors.toList());
        assertThat(cvvs,not(contains(cvv)));
    }
}
