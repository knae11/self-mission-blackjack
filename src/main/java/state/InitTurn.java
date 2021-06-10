package state;

import card.Card;
import card.Cards;
import exception.card.CardCannotTakeException;

import java.util.List;

public class InitTurn extends Running {
    private final Cards cards;

    public InitTurn() {
        this.cards = new Cards();
    }

    public InitTurn(List<Card> cards) {
        this.cards = new Cards(cards);
    }

    @Override
    public State takeCard(Card card) {
        throw new CardCannotTakeException();
    }

    @Override
    public State takeCards(List<Card> cards) {
        this.cards.add(cards);
        if (this.cards.calculateFinalScore() == BLACKJACK) {
            return new Blackjack(this.cards);
        }
        return new Hit(this.cards);
    }

    @Override
    public int calculateScore() {
        return cards.calculateScoreAceAsOne();
    }

    @Override
    public boolean hasCardSizeOf(int size) {
        return cards.isSizeOf(size);
    }
}
