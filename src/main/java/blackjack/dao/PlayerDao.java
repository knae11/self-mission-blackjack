package blackjack.dao;

import blackjack.domain.participant.Participant;
import blackjack.domain.participant.Player;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PlayerDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public PlayerDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("PLAYERS")
                .usingGeneratedKeyColumns("player_id")
                .usingColumns("cards","money", "win", "draw", "lose", "state", "room_id");
    }

    public List<Player> create(List<Player> players) {
        List<>
        players.stream()
                .map(player->{
                    Map<String, Object> params = new HashMap<>();
                    params.put("cards", player.getCompressedCardIds());
                    params.put("money", player.getInitialBetting());
                    params.put("state", player.getState())
                })
        return simpleJdbcInsert.executeBatch() AndReturnKey(params).longValue();
    }
}
