package blackjack.dao;

import blackjack.application.StateFinder;
import blackjack.domain.card.Card;
import blackjack.domain.card.Cards;
import blackjack.domain.participant.Dealer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcDealerRepository implements DealerRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcDealerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Dealer findByGameId(Long gameId) {
        return findDealer(gameId);
    }

    private Dealer findDealer(Long gameId) {
        String dealerSql = "SELECT dealer_id, state FROM dealer WHERE game_id = ?";
        return jdbcTemplate.queryForObject(dealerSql, dealerRowMapper(), gameId);
    }

    private RowMapper<Dealer> dealerRowMapper() {
        return (rs, rowNum) -> {
            long dealerId = rs.getLong("dealer_id");
            String stateName = rs.getString("state");

            String cardsSql = "SELECT card FROM dealer_card WHERE dealer_id = ? ORDER BY card_id";
            List<Card> cards = jdbcTemplate.queryForList(cardsSql, Card.class, dealerId);

            return new Dealer(dealerId, StateFinder.findState(stateName, new Cards(cards)));
        };
    }

    @Override
    public void update(Dealer dealer, List<Card> newCards) {
        String playerSql = "UPDATE dealer SET state = ? WHERE dealer_id = ?";
        jdbcTemplate.update(playerSql, dealer.getStateToString(), dealer.getId());

        String cardSql = "INSERT INTO player_card (card, player_id) VALUES (?, ?)";

        newCards.forEach(card ->
                jdbcTemplate.update(cardSql, card.getCardId(), dealer.getId()));
    }

}
