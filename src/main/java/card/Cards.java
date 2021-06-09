package card;

import java.util.ArrayList;
import java.util.List;

public class Cards {
    private final List<Card> cards;

    public Cards() {
        this.cards = new ArrayList<>();
    }

    public boolean isSizeOf(int size) {
        return cards.size() == size;
    }

    public void add(List<Card> values) {
        values.forEach(this.cards::add);
    }

    public void add(Card card) {
        this.cards.add(card);
    }

    public int calculateScore() {
        return this.cards.stream()
                .mapToInt(Card::getScore)
                .reduce(Integer::sum)
                .getAsInt();

    }
}
