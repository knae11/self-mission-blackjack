package blackjack.domain.card;

import blackjack.exception.domain.card.CardEmptyException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cards {
    private static final int ONE = 1;
    private static final int EXTRA_ACE_SCORE = 10;
    private static final int ZERO = 0;
    private static final int MAX_NUMBER_OF_NOT_BUST = 21;
    private final List<Card> cards;

    public Cards() {
        this(Collections.emptyList());
    }

    public Cards(List<Card> cards) {
        this.cards = new ArrayList<>(cards);

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

    public int calculateScoreAceAsOne() {
        return this.cards.stream()
                .mapToInt(Card::getScore)
                .reduce(Integer::sum)
                .orElseThrow(CardEmptyException::new);

    }

    public int calculateFinalScore() {
        int aceCount = getAceCount();
        int score = calculateScoreAceAsOne();
        while (score + EXTRA_ACE_SCORE <= MAX_NUMBER_OF_NOT_BUST && aceCount > ZERO) {
            score += EXTRA_ACE_SCORE;
            aceCount -= ONE;
        }
        return score;
    }

    private int getAceCount() {
        return (int) this.cards.stream()
                .filter(Card::isAce)
                .count();
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }
}
