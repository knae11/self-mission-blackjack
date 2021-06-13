package blackjack.domain.participant;

import blackjack.application.StateFinder;
import blackjack.domain.card.Card;
import blackjack.domain.state.InitTurn;
import blackjack.domain.state.State;

import java.util.List;

public class Player implements Participant {
    private static final String DEFAULT_NAME = "any";
    private static final int DEFAULT_BETTING_MONEY = 0;

    private final Long id;
    private final String name;
    private final int initialBetting;

    private State state;

    public Player(Long id, String name, int initialBetting, State state) {
        this.id = id;
        this.name = name;
        this.initialBetting = initialBetting;
        this.state = state;
    }

    public Player(String name, int bettingMoney, State state) {
        this(null, name, bettingMoney, state);
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

    public Player(Long id, Player player) {
        this(id, player.name, player.initialBetting, player.state);
;    }

    @Override
    public State takeCards(List<Card> cards) {
        this.state = state.takeCardsForPlayer(cards);
        return state;
    }

    @Override
    public State takeCardForPlayer(boolean acceptance, Card value) {
        this.state = state.takeCardForPlayer(acceptance, value);
        return state;
    }

    @Override
    public State takeCardForDealer(Card value) {
        throw new IllegalArgumentException();
    }

    @Override
    public boolean hasCardSizeOf(int size) {
        return state.hasCardSizeOf(size);
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

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public int getInitialBetting() {
        return initialBetting;
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
