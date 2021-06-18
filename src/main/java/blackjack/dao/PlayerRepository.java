package blackjack.dao;

import blackjack.domain.card.Card;
import blackjack.domain.participant.Player;

import java.util.List;

public interface PlayerRepository {
    List<Player> findPlayersByGameId(Long gameId);

    Player findByIds(Long gameId, Long playerId);

    void update(Player player, List<Card> newCards);
}
