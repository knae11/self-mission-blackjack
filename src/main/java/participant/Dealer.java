package participant;

import card.Card;
import card.Cards;

import java.util.List;

public class Dealer implements Participant {
    private final Cards cards;

    public Dealer() {
        this.cards = new Cards();
    }

    @Override
    public boolean hasCardSizeOf(int size) {
        return cards.isSizeOf(size);
    }

    @Override
    public void takeCards(List<Card> values) {
        cards.add(values);
    }

    @Override
    public void takeCard(Card value) {
        cards.add(value);
    }

    @Override
    public boolean isAbleToTake() {
        return cards.calculateScoreAceAsOne() <= 16;
    }
}
