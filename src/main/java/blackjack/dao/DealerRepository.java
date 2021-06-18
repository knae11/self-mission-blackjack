package blackjack.dao;

import blackjack.domain.card.Card;
import blackjack.domain.participant.Dealer;

import java.util.List;

public interface DealerRepository {
    Dealer findByGameId(Long gameId);

    void update(Dealer dealer, List<Card> newCards);
}
