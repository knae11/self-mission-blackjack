package domain.state;

import domain.card.Cards;

public class Bust extends Finished {
    public Bust(Cards cards) {
        super(cards);
    }

    @Override
    public boolean isBust() {
        return true;
    }
}
