package blackjack.domain.card;

import blackjack.exception.card.CardInvalidException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Card {
    private static final Map<String, Card> cache;

    static {
        cache = new HashMap<>();
        for (Suit suit : Suit.values()) {
            for (Denomination denomination : Denomination.values()) {
                cache.put(suit.getId() + denomination.getId(), new Card(suit, denomination));
            }
        }
    }

    private final Suit suit;
    private final Denomination denomination;

    private Card(Suit suit, Denomination denomination) {
        this.suit = suit;
        this.denomination = denomination;
    }

    public static Card of(Suit suit, Denomination denomination) {
      return of(suit.getId() + denomination.getId());
    }

    public static Card of(String cardId) {
        if (cache.containsKey(cardId)) {
            return cache.get(cardId);
        }
        throw new CardInvalidException();
    }

    public static List<Card> getAllCards() {
        return new ArrayList(cache.values());
    }

    public String getCardId() {
        return suit.getId() + denomination.getId();
    }

    @Override
    public String toString() {
        return "Card{" +
                "suit=" + suit +
                ", denomination=" + denomination +
                '}';
    }

    public int getScore() {
        return denomination.getScore();
    }

    public boolean isAce() {
        return denomination == Denomination.ACE;
    }
}
