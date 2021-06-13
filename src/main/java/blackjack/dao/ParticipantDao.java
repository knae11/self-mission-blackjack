package blackjack.dao;

import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Player;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ParticipantDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ParticipantDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("participant")
                .usingGeneratedKeyColumns("participant_id")
                .usingColumns("is_player", "name", "initial_betting");
    }

    public Player createPlayer(Player player) {

        Map<String, Object> params = new HashMap<>();
        params.put("is_player", player.isPlayer());
        params.put("name", player.getName());
        params.put("initial_betting", player.getInitialBetting());
        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new Player(id, player);
    }

    public Dealer createDealer(Dealer dealer) {
        Map<String, Object> params = new HashMap<>();
        params.put("is_player", dealer.isPlayer());
        params.put("name", dealer.getName());
        params.put("initial_betting", dealer.getInitialBetting());
        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new Dealer(id, dealer);
    }

}
