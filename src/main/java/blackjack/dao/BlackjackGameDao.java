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
    private final SimpleJdbcInsert simpleJdbcInsert;

    public BlackjackGameDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("blackjackgame")
                .usingGeneratedKeyColumns("game_id");
    }

    public Long createRoom(String deck) {
        Map<String, Object> params = new HashMap<>();
        params.put("deck", deck);
        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }
}
