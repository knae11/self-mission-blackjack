package blackjack.domain.state;

import blackjack.domain.card.Card;

import java.util.List;

public interface State {

    boolean isBlackjack();

    boolean isBust();

    boolean isStay();

    State takeCardForPlayer(boolean acceptance, Card card);

    State takeCardForDealer(Card card);

    State takeCardsForPlayer(List<Card> cards);

    State takeCardsForDealer(List<Card> cards);

    int calculateScore();

    boolean isRunning();

    boolean hasCardSizeOf(int size);

    List<Card> getCards();
}
