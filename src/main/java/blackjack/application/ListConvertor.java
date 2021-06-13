package blackjack.application;

import blackjack.domain.card.Card;
import blackjack.domain.participant.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ListConvertor {
    private static final String DELIMITER = ",";

    public static String compressPlayerIds(List<Player> players) {
        return players.stream()
                .map(Player::getId)
                .map(String::valueOf)
                .collect(Collectors.joining(DELIMITER));
    }

    public static String compressCardIds(List<Card> cards) {
        return cards.stream()
                .map(Card::getCardId)
                .collect(Collectors.joining(DELIMITER));
    }

    public static List<Long> depressPlayerIds(String playerIds) {
        String[] split = playerIds.split(DELIMITER);
        return Arrays.stream(split)
                .map(Long::valueOf)
                .collect(Collectors.toList());
    }

    public static List<Card> depressCardIds(String cardIds) {
        String[] cardIdValues = cardIds.split(DELIMITER);
        return Arrays.stream(cardIdValues)
                .map(cardId -> Card.of(cardId))
                .collect(Collectors.toList());
    }
}
