package blackjack.dao;

import blackjack.application.ListConvertor;
import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Participant;
import blackjack.domain.state.State;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

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
