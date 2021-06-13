package blackjack.domain.participant;

import blackjack.domain.card.Card;
import blackjack.domain.card.Cards;
import blackjack.domain.state.InitTurn;
import blackjack.domain.state.State;

import java.util.Collections;
import java.util.List;

public class Player implements Participant {
    private static final String DEFAULT_NAME = "any";
    private static final int DEFAULT_BETTING_MONEY = 0;

    private final int initialBetting;
    private final String name;
    private final Long id;

    private State state;

    public Player(Long id, String name, int bettingMoney, State state) {
        this.id = id;
        this.name = name;
        this.initialBetting = bettingMoney;
        this.state = state;
    }

    public Player(String name, int bettingMoney, State state) {
        this(null, name, bettingMoney, state);
    }

    public Player(String name, int bettingMoney) {
        this(null, name, bettingMoney, new InitTurn());
    }

    public Player(Long id, String name, int bettingMoney) {
        this(id, name, bettingMoney, new InitTurn());
    }

    public Player(List<Card> cards) {
        this(null, DEFAULT_NAME, DEFAULT_BETTING_MONEY, new InitTurn(cards));
    }

    public Player() {
        this(DEFAULT_NAME, DEFAULT_BETTING_MONEY);
    }

    public Player(State state) {
        this(null, DEFAULT_NAME, DEFAULT_BETTING_MONEY, state);
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

    @Override
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

    @Override
    public Long getId() {
        return id;
    }

    public int getInitialBetting() {
        return initialBetting;
    }

    public String getCompressedCardIds(){
        Cards cards = state.getCards();
        return String.join("/",cards.getCardIds());
    }

    public State getState(){
        return state;
    }

}
