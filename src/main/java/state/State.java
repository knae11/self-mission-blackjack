package state;

import card.Card;

public interface State {

    boolean isBlackjack();

    void takeCard(Card card);

    int calculateScore();
}
