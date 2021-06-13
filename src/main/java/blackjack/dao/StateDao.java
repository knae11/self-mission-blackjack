package blackjack.dao;

import blackjack.application.ListConvertor;
import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Participant;
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

    public void create(Participant participant) {
        Map<String, Object> params = new HashMap<>();
        params.put("participant_id", participant.getId());
        params.put("name", participant.getStateToString());
        params.put("card_ids", ListConvertor.compressCardIds(participant.getCards()));

        stateInsertAction.executeAndReturnKey(params).longValue();
    }
}
