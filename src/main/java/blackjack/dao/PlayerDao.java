package blackjack.dao;

import blackjack.application.StateFinder;
import blackjack.domain.card.Card;
import blackjack.domain.card.Cards;
import blackjack.domain.participant.Player;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlayerDao {

    private final JdbcTemplate jdbcTemplate;

    public PlayerDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Player> findPlayersByGameId(Long gameId) {
        return findPlayers(gameId);
    }

    private List<Player> findPlayers(Long gameId) {
        String playerSql = "SELECT player_id, state, name, initial_betting FROM player WHERE game_id = ?";
        return jdbcTemplate.query(playerSql, playerRowMapper(), gameId);
    }

    private RowMapper<Player> playerRowMapper() {
        return (rs, rowNum) -> {
            long playerId = rs.getLong("player_id");
            String stateName = rs.getString("state");
            String name = rs.getString("name");
            int initialBetting = rs.getInt("initial_betting");

            String cardsSql = "SELECT card FROM player_card WHERE player_id = ? ORDER BY card_id";
            List<Card> cards = jdbcTemplate.queryForList(cardsSql, Card.class, playerId);

            return new Player(playerId, name, initialBetting, StateFinder.findState(stateName, new Cards(cards)));
        };
    }

    public Player findByIds(Long gameId, Long playerId) {
        String playerSql = "SELECT player_id, state, name, initial_betting FROM player WHERE game_id = ? AND player_id = ?";
        return jdbcTemplate.queryForObject(playerSql, playerRowMapper(), gameId, playerId);
    }

    public void update(Player player, List<Card> newCards) {
        String playerSql = "UPDATE player SET state = ? WHERE player_id = ?";
        jdbcTemplate.update(playerSql, player.getStateToString(), player.getId());

        String cardSql = "INSERT INTO player_card (card, player_id) VALUES (?, ?)";

        newCards.forEach(card ->
                jdbcTemplate.update(cardSql, card.getCardId(), player.getId()));
    }
}
