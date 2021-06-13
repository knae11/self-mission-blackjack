package blackjack.dao;

import blackjack.application.ListConvertor;
import blackjack.application.StateFinder;
import blackjack.domain.participant.Player;
import blackjack.domain.state.State;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class StateDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert stateInsertAction;

    public StateDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.stateInsertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("state")
                .usingGeneratedKeyColumns("state_id")
                .usingColumns("participant_id", "name", "card_ids");

    }

    public void updateByPlayer(Player player) {
        String sql = "UPDATE state SET name = ?, card_ids = ? WHERE participant_id = ?";
        jdbcTemplate.update(sql, player.getStateToString(), ListConvertor.compressCardIds(player.getCards()), player.getId());
    }
}
