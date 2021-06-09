package state;

import card.Card;
import card.Cards;
import exception.card.CardCannotTakeException;

import java.util.List;

public class Hit extends Running {
    private final Cards cards;

    public Hit(Cards cards) {
        this.cards = cards;
    }


    @Override
    public State takeCard(Card card) {
        cards.add(card);
        if (cards.calculateFinalScore() > 21) {
            return new Bust(cards);
        }
        return new Hit(cards);
    }

    @Override
    public State takeCards(List<Card> cards) {
        throw new CardCannotTakeException();
    }

    @Override
    public int calculateScore() {
        return cards.calculateScoreAceAsOne();
    }
}
