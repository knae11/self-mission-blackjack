package state;

import card.Card;
import card.Cards;
import exception.card.GameIsDoneException;

public abstract class Finished {
    private final Cards cards;

    protected Finished(Cards cards) {
        this.cards = cards;
    }

    abstract boolean isBlackjack();

    void takeCard(Card card) {
        throw new GameIsDoneException();
    }

    int calculateScore() {
        return cards.calculateScoreAceAsOne();
    }
}
