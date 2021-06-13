package blackjack.application;

import blackjack.domain.card.Card;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CardConvertor {
    private static final String DELIMITER = "/";

    public static String compressCards(List<Card> cards){
        List<String> cardValues = cards.stream()
                .map(Card::getCardId)
                .collect(Collectors.toList());

        return String.join(DELIMITER, cardValues);
    }

    public static List<Card> depressCards(String cardValues){
        String[] cardIds = cardValues.split(DELIMITER);
        return Arrays.stream(cardIds)
                .map(Card::of)
                .collect(Collectors.toList());
    }
}
