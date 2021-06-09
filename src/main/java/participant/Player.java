package participant;

import card.Card;
import card.Cards;

import java.util.List;

public class Player implements Participant {
    private final Cards cards;

    public Player(String name, int bettingMoney) {
        cards = new Cards();
    }

    public Player() {
        this("any", 0);
    }

    @Override
    public void takeCards(List<Card> cards) {
        this.cards.add(cards);
    }

    @Override
    public boolean hasCardSizeOf(int size) {
        return cards.isSizeOf(size);
    }
}
