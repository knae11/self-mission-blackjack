package blackjack.domain.participant;

import blackjack.domain.card.Card;
import blackjack.domain.state.State;

import java.util.List;

public interface Participant {

    boolean hasCardSizeOf(int size);

    State takeCards(List<Card> values);

    State takeCard(boolean acceptance, Card value);

    boolean isAbleToTake();

    boolean isRunning();

    boolean isBlackjack();

    boolean isBust();

    boolean isStay();

    int calculateScore();

    boolean isPlayer();

    String getName();

}
