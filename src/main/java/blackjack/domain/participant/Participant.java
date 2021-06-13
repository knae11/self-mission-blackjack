package blackjack.domain.participant;

import blackjack.domain.card.Card;
import blackjack.domain.state.State;

import java.util.List;

public interface Participant {

    boolean hasCardSizeOf(int size);

    State takeCards(List<Card> values);

    State takeCardForPlayer(boolean acceptance, Card value);

    State takeCardForDealer(Card value);

    boolean isRunning();

    boolean isBlackjack();

    boolean isBust();

    boolean isStay();

    int calculateScore();

    boolean isPlayer();

    String getName();

    Long getId();

    int getInitialBetting();

    String getStateToString();

    List<Card> getCards();
}
