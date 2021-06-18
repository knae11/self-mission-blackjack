package blackjack.dao;

import blackjack.domain.card.Card;
import blackjack.domain.card.Deck;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DeckCardDao {
    private final JdbcTemplate jdbcTemplate;

    public DeckCardDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void update(Deck deck, Long gameId) {
        List<Card> previousCards = findPreviousCards(gameId);
        previousCards.removeAll(deck.getCards());

        previousCards.forEach(card -> {
            String sql = "UPDATE deck_card SET is_used = false WHERE (game_id = ? AND card = ?)";
            jdbcTemplate.update(sql, gameId, card.getCardId());
        });
    }

    private List<Card> findPreviousCards(Long gameId) {
        String cardsSql = "SELECT card FROM deck_card WHERE game_id = ?";
        return jdbcTemplate.queryForList(cardsSql, Card.class, gameId);
    }
}
