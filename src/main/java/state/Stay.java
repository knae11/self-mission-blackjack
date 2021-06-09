package state;

import card.Cards;

public class Stay extends Finished {

    protected Stay(Cards cards) {
        super(cards);
    }

    @Override
    public boolean isBlackjack() {
        return false;
    }

}
