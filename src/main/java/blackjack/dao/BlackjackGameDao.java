package blackjack.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class BlackjackGameDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertAction;

    public BlackjackGameDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("blackjackgame")
                .usingGeneratedKeyColumns("game_id")
                .usingColumns("dealer_id", "player_ids", "deck_id");
    }

    public Long create(Long dealerId, String playerIds, Long deckId) {
        Map<String, Object> params = new HashMap<>();
        params.put("dealer_id", dealerId);
        params.put("player_ids", playerIds);
        params.put("deck_id", deckId);
        return insertAction.executeAndReturnKey(params).longValue();
    }
}
