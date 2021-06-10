package participant;

import card.Card;

import java.util.List;

interface Participant {

    boolean hasCardSizeOf(int size);

    void takeCards(List<Card> values);

    void takeCard(Card value);

    boolean isAbleToTake();

    boolean isRunning();

    boolean isBlackjack();

    boolean isBust();

    boolean isStay();

    int calculateScore();
}
