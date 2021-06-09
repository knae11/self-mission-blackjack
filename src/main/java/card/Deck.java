package card;

import java.util.*;

public class Deck {

    private final List<Card> cards;

    private Deck(List<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    public static Deck createBasic() {
        return new Deck(Card.getAllCards());
    }

    public static Deck createShuffled() {
        List<Card> cards = new ArrayList<>(Card.getAllCards());
        Collections.shuffle(cards);
        return new Deck(cards);
    }

    public static Deck listOf(List<Card> cards) {
        return new Deck(cards);
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }
}
