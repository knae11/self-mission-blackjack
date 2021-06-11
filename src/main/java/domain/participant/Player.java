package domain.participant;

import domain.card.Card;
import domain.state.InitTurn;
import domain.state.State;

import java.util.List;

public class Player implements Participant {
    private static final String DEFAULT_NAME = "any";
    private static final int DEFAULT_BETTING_MONEY = 0;

    private final int initialBetting;
    private final String name;

    private State state;

    public Player(String name, int bettingMoney, State state) {
        this.name = name;
        this.initialBetting = bettingMoney;
        this.state = state;
    }

    public Player(String name, int bettingMoney) {
        this(name, bettingMoney, new InitTurn());
    }

    public Player(List<Card> cards) {
        this(DEFAULT_NAME, DEFAULT_BETTING_MONEY, new InitTurn(cards));
    }

    public Player() {
        this(DEFAULT_NAME, DEFAULT_BETTING_MONEY);
    }

    public Player(State state) {
        this(DEFAULT_NAME, DEFAULT_BETTING_MONEY, state);
    }

    @Override
    public void takeCards(List<Card> cards) {
        this.state = state.takeCards(cards);
    }

    @Override
    public void takeCard(Card value) {
        this.state = state.takeCard(value);
    }

    @Override
    public boolean hasCardSizeOf(int size) {
        return state.hasCardSizeOf(size);
    }

    public boolean isAbleToTake() {
        return state.calculateScore() <= 21;
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
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getInitialBetting() {
        return initialBetting;
    }
}
