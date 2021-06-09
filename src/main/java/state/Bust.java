package state;

import card.Cards;

public class Bust extends Finished {
    public Bust(Cards cards) {
        super(cards);
    }

    @Override
    public boolean isBlackjack() {
        return false;
    }
}
