package blackjack.dao;

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

}
