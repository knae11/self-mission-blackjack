package blackjack.application;

import blackjack.domain.card.Card;
import blackjack.domain.participant.Player;

import java.util.List;
import java.util.stream.Collectors;

public class ListConvertor {
    private static final String DELIMITER = ",";

    public static String compressPlayerIds(List<Player> players){
       return players.stream()
               .map(Player::getId)
               .map(String::valueOf)
               .collect(Collectors.joining(DELIMITER));
    }

    public static String compressCardIds(List<Card> cards){
        return  cards.stream()
                .map(Card::getCardId)
                .collect(Collectors.joining(DELIMITER));
    }

}
