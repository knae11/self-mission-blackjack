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
        if (cache.containsKey(suit.getId() + denomination.getId())) {
            return cache.get(suit.getId() + denomination.getId());
        }
        throw new CardInvalidException();
    }

    public static List<Card> getAllCards() {
        return new ArrayList(cache.values());
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
