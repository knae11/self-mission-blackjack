package participant;

import card.Card;
import card.Cards;
import state.InitTurn;
import state.State;

import java.util.List;

public class Dealer implements Participant {
    private State state;

    public Dealer() {
        this.state = new InitTurn();
    }

    @Override
    public boolean hasCardSizeOf(int size) {
        return state.hasCardSizeOf(size);
    }

    @Override
    public void takeCards(List<Card> values) {
        this.state = state.takeCards(values);
    }

    @Override
    public void takeCard(Card value) {
        this.state = state.takeCard(value);
    }

    @Override
    public boolean isAbleToTake() {
        return this.state.calculateScore() <= 16;
    }

    @Override
    public boolean isRunning() {
        return state.isRunning();
    }
}
