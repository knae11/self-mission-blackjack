package blackjack.domain.state;

import blackjack.domain.card.Card;
import blackjack.domain.card.Cards;
import blackjack.exception.card.CardCannotTakeException;

import java.util.ArrayList;
import java.util.List;

abstract class Finished implements State {
    private final Cards cards;

    protected Finished(Cards cards) {
        this.cards = cards;
    }

    @Override
    public State takeCardForPlayer(boolean acceptance, Card card) {
        throw new CardCannotTakeException();
    }

    @Override
    public State takeCardForDealer(Card card) {
        throw new CardCannotTakeException();
    }

    @Override
    public State takeCardsForPlayer(List<Card> cards) {
        throw new CardCannotTakeException();
    }

    @Override
    public State takeCardsForDealer(List<Card> cards) {
        throw new CardCannotTakeException();
    }

    @Override
    public int calculateScore() {
        return cards.calculateFinalScore();
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public boolean hasCardSizeOf(int size) {
        return cards.isSizeOf(size);
    }

    @Override
    public boolean isBlackjack() {
        return false;
    }

    @Override
    public boolean isBust() {
        return false;
    }

    @Override
    public boolean isStay() {
        return false;
    }

    @Override
    public List<Card> getCards() {
        return cards.getCards();
    }
}
