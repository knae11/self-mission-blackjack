package blackjack.domain.card;

import blackjack.application.ListConvertor;
import blackjack.exception.domain.card.CardEmptyException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Deck {
    private static final int ZERO = 0;

    private final Long id;
    private final List<Card> cards;

    private Deck(Long id, List<Card> cards) {
        this.id = id;
        this.cards = cards;
    }

    private Deck(List<Card> cards) {
        this(null, new ArrayList<>(cards));
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

    public static Deck of(long id, Deck deck) {
        return new Deck(id, deck.cards);
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }

    public String getCardIds() {
        return ListConvertor.compressCardIds(cards);
    }

    public Card drawCard() {
        if (cards.size() <= ZERO) {
            throw new CardEmptyException();
        }
        return cards.remove(cards.size() - 1);
    }

    public List<Card> drawTwoCards() {
        return Arrays.asList(drawCard(), drawCard());
    }

    public Long getId() {
        return id;
    }
}
