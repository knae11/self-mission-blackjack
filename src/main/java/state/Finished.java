package state;

import card.Card;
import card.Cards;
import exception.card.GameIsDoneException;

public abstract class Finished implements State {
    private final Cards cards;

    protected Finished(Cards cards) {
        this.cards = cards;
    }

    @Override
    public void takeCard(Card card) {
        throw new GameIsDoneException();
    }

    @Override
    public int calculateScore() {
        return cards.calculateFinalScore();
    }
}
