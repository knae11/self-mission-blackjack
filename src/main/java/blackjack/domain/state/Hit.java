package blackjack.domain.state;

import blackjack.domain.card.Card;
import blackjack.domain.card.Cards;
import blackjack.exception.card.CardCannotTakeException;

import java.util.List;

public class Hit extends Running {
    private final Cards cards;

    public Hit(Cards cards) {
        this.cards = cards;
    }

    @Override
    public State takeCardForPlayer(boolean acceptance, Card card) {
        if (acceptance) {
            cards.add(card);
            return determineState();
        }
        return new Stay(cards);
    }

    @Override
    public State takeCardForDealer(Card card) {
        cards.add(card);
        return new Stay(cards);
    }

    @Override
    public State takeCardsForPlayer(List<Card> cards) {
        throw new CardCannotTakeException();
    }

    @Override
    public State takeCardsForDealer(List<Card> cards) {
        throw new CardCannotTakeException();
    }

    private State determineState() {
        if (cards.calculateFinalScore() > BLACKJACK) {
            return new Bust(cards);
        }
        return new Hit(cards);
    }

    @Override
    public int calculateScore() {
        return cards.calculateScoreAceAsOne();
    }

    @Override
    public boolean hasCardSizeOf(int size) {
        return cards.isSizeOf(size);
    }

    @Override
    public List<Card> getCards() {
        return cards.getCards();
    }
}
