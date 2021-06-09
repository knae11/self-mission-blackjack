package state;

import card.Cards;

public class Blackjack extends Finished {
    public Blackjack(Cards cards) {
        super(cards);
    }

    public boolean isBlackjack() {
        return true;
    }
}
