package participant;

import card.Card;

import java.util.List;

interface Participant {

    boolean hasCardSizeOf(int size);

    void takeCards(List<Card> asList);
}
