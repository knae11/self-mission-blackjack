package blackjack.dao;

import blackjack.application.ListConvertor;
import blackjack.domain.card.Card;
import blackjack.domain.card.Deck;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DeckDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public DeckDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("deck")
                .usingGeneratedKeyColumns("deck_id")
                .usingColumns("card_ids");
    }

    public Deck create(Deck deck) {
        Map<String, Object> params = new HashMap<>();
        params.put("card_ids", deck.getCardIds());
        long deckId = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return Deck.of(deckId, deck);
    }

    public Deck findDeckById(Long deckId) {
        String sql = "SELECT (card_ids) FROM deck WHERE deck_id = ?";
        String cardIds = jdbcTemplate.queryForObject(sql, String.class, deckId);
        List<Card> cards = ListConvertor.depressCardIds(cardIds);
        return Deck.listOf(cards);
    }
}
