package blackjack.domain.state;

import blackjack.domain.card.Card;
import blackjack.domain.card.Cards;
import blackjack.exception.card.CardCannotTakeException;

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
    public State takeCardForPlayer(boolean acceptance, Card card) {
        throw new CardCannotTakeException();
    }

    @Override
    public State takeCardForDealer(Card card) {
        throw new CardCannotTakeException();
    }

    @Override
    public State takeCardsForPlayer(List<Card> cards) {
        this.cards.add(cards);
        if (this.cards.calculateFinalScore() == BLACKJACK) {
            return new Blackjack(this.cards);
        }
        return new Hit(this.cards);
    }

    @Override
    public State takeCardsForDealer(List<Card> cards) {
        this.cards.add(cards);
        if (calculateScore() > 16) {
            return new Stay(this.cards);
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
