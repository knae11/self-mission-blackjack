package blackjack.domain.participant;

import blackjack.domain.card.Card;
import blackjack.domain.state.InitTurn;
import blackjack.domain.state.State;

import java.util.List;

public class Dealer implements Participant {
    private static final String DEFAULT_DEALER = "딜러";
    private final String name;
    private State state;

    public Dealer(String name, State state) {
        this.name = name;
        this.state = state;
    }

    public Dealer() {
        this(DEFAULT_DEALER, new InitTurn());
    }

    public Dealer(List<Card> cards) {
        this(DEFAULT_DEALER, new InitTurn(cards));
    }

    public Dealer(State state) {
        this(DEFAULT_DEALER, state);
    }

    @Override
    public boolean hasCardSizeOf(int size) {
        return state.hasCardSizeOf(size);
    }

    @Override
    public State takeCards(List<Card> values) {
        this.state = state.takeCards(isPlayer(), values);
        return state;
    }

    @Override
    public State takeCard(boolean acceptance, Card value) {
        this.state = state.takeCard(acceptance, value);
        return state;
    }

    @Override
    public boolean isAbleToTake() {
        return this.state.calculateScore() <= 16;
    }

    @Override
    public boolean isRunning() {
        return state.isRunning();
    }

    @Override
    public boolean isBlackjack() {
        return state.isBlackjack();
    }

    @Override
    public boolean isBust() {
        return state.isBust();
    }

    @Override
    public boolean isStay() {
        return state.isStay();
    }

    @Override
    public int calculateScore() {
        return state.calculateScore();
    }

    @Override
    public boolean isPlayer() {
        return false;
    }

    @Override
    public String getName() {
        return name;
    }
}
