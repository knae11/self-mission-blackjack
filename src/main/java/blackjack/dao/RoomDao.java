package blackjack.dao;

import blackjack.domain.card.Deck;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class RoomDao {
    private static final String DELIMITER = "/";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public RoomDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("ROOMS")
                .usingGeneratedKeyColumns("room_id");
    }

    public Long createRoom(String deck) {
        Map<String, Object> params = new HashMap<>();
        params.put("deck", deck);
        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }

    public String findDeckValues(Long roomId) {
        String sql = "SELECT deck FROM ROOMS WHERE room_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, roomId);
    }
}
