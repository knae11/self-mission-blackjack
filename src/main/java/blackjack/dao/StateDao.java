package blackjack.dao;

import blackjack.application.ListConvertor;
import blackjack.domain.participant.Participant;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StateDao {
    private final JdbcTemplate jdbcTemplate;

    public StateDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void updateByParticipant(Participant participant) {
        String sql = "UPDATE state SET name = ?, card_ids = ? WHERE participant_id = ?";
        jdbcTemplate.update(sql, participant.getStateToString(), ListConvertor.compressCardIds(participant.getCards()), participant.getId());
    }

}
