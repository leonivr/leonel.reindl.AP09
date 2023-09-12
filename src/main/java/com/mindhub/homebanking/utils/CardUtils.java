package com.mindhub.homebanking.utils;

public final class CardUtils {
    private CardUtils() {
    }

    public static String getCardNumber() {
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
    }
    public static int getCvv(){
        return (int) ((Math.random() * (999 - 100)) + 100);
    }
}
