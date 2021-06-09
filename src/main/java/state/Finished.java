package state;

import card.Card;
import card.Cards;
import exception.card.CardCannotTakeException;

import java.util.List;

abstract class Finished implements State {
    private final Cards cards;

    protected Finished(Cards cards) {
        this.cards = cards;
    }

    @Override
    public State takeCard(Card card) {
        throw new CardCannotTakeException();
    }

    @Override
    public State takeCards(List<Card> cards) {
        throw new CardCannotTakeException();
    }

    @Override
    public int calculateScore() {
        return cards.calculateFinalScore();
    }
}
