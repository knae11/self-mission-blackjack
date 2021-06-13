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
    public State takeCard(Card card) {
        cards.add(card);
        if (cards.calculateFinalScore() > BLACKJACK) {
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

    @Override
    public boolean hasCardSizeOf(int size) {
        return cards.isSizeOf(size);
    }

    @Override
    public Cards getCards() {
        return cards;
    }
}
