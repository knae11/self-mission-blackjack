package participant;

import card.Card;
import state.InitTurn;
import state.State;

import java.util.List;

public class Player implements Participant {
    private State state;

    public Player(String name, int bettingMoney) {
        this.state = new InitTurn();
    }

    public Player() {
        this("any", 0);
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
}
