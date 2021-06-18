package blackjack.dao;

import blackjack.domain.card.Deck;

public interface DeckCardRepository {
    void update(Deck deck, Long gameId);
}
