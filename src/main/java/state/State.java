package state;

import card.Card;

import java.util.List;

public interface State {

    boolean isBlackjack();

    State takeCard(Card card);

    State takeCards(List<Card> cards);

    int calculateScore();
}
