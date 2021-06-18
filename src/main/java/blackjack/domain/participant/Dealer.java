package blackjack.domain.participant;

import blackjack.application.StateFinder;
import blackjack.domain.card.Card;
import blackjack.domain.state.InitTurn;
import blackjack.domain.state.State;
import blackjack.exception.domain.card.CardCannotTakeException;

import java.util.List;

public class Dealer implements Participant {
    private static final String DEFAULT_DEALER = "딜러";
    private static final int DEFAULT_INITIAL_BETTING = 0;

    private final Long id;
    private State state;

    public Dealer(Long id, State state) {
        this.id = id;
        this.state = state;
    }

    public Dealer() {
        this(null, new InitTurn());
    }

    public Dealer(List<Card> cards) {
        this(null, new InitTurn(cards));
    }

    public Dealer(State state) {
        this(null, state);
    }

    public Dealer(Long id, Dealer dealer) {
        this(id, dealer.state);
    }

    public static Dealer create(Long id, Dealer dealer) {
        return new Dealer(id, dealer.state);
    }

    @Override
    public boolean hasCardSizeOf(int size) {
        return state.hasCardSizeOf(size);
    }

    @Override
    public State takeCards(List<Card> values) {
        this.state = state.takeCardsForDealer(values);
        return state;
    }

    @Override
    public State takeCardForPlayer(boolean acceptance, Card value) {
        throw new CardCannotTakeException();
    }

    @Override
    public State takeCardForDealer(Card value) {
        this.state = state.takeCardForDealer(value);
        return state;
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
        return DEFAULT_DEALER;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public int getInitialBetting() {
        return DEFAULT_INITIAL_BETTING;
    }

    @Override
    public String getStateToString() {
        return StateFinder.convertToString(state);
    }

    @Override
    public List<Card> getCards() {
        return state.getCards();
    }
}
