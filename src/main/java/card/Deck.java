package card;

import exception.card.DeckEmptyException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Deck {
    private static final int ZERO = 0;

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

    public Card drawCard() {
        if(cards.size() <= ZERO){
            throw new DeckEmptyException();
        }
        return cards.remove(cards.size() - 1);
    }

    public List<Card> drawTwoCards() {
        return Arrays.asList(drawCard(), drawCard());
    }
}
