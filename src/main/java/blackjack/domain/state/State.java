package blackjack.domain.state;

import blackjack.domain.card.Card;

import java.util.List;

public interface State {

    boolean isBlackjack();

    boolean isBust();

    boolean isStay();

    State takeCard(boolean acceptance, Card card);

    State takeCards(boolean isPlayer, List<Card> cards);

    int calculateScore();

    boolean isRunning();

    boolean hasCardSizeOf(int size);
}
