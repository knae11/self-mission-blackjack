package domain.state;

import domain.card.Card;

import java.util.List;

public interface State {

    boolean isBlackjack();
    boolean isBust();
    boolean isStay();

    State takeCard(Card card);

    State takeCards(List<Card> cards);

    int calculateScore();

    boolean isRunning();

    boolean hasCardSizeOf(int size);
}
