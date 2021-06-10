package state;

import card.Cards;

public class Stay extends Finished {

    public Stay(Cards cards) {
        super(cards);
    }

    @Override
    public boolean isStay() {
        return true;
    }

}
