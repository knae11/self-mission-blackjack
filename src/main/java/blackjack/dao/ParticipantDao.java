package blackjack.dao;

import blackjack.application.ListConvertor;
import blackjack.application.StateFinder;
import blackjack.domain.card.Cards;
import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Participant;
import blackjack.domain.participant.Player;
import blackjack.domain.state.State;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ParticipantDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert participantInsertAction;
    private final SimpleJdbcInsert stateInsertAction;

    public ParticipantDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.participantInsertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("participant")
                .usingGeneratedKeyColumns("participant_id")
                .usingColumns("is_player", "name", "initial_betting");
        this.stateInsertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("state")
                .usingGeneratedKeyColumns("state_id")
                .usingColumns("participant_id", "name", "card_ids");

    }

    public Player createPlayer(Player player) {
        Map<String, Object> params = new HashMap<>();
        params.put("is_player", player.isPlayer());
        params.put("name", player.getName());
        params.put("initial_betting", player.getInitialBetting());
        Long id = participantInsertAction.executeAndReturnKey(params).longValue();
        createState(id, player);

        return new Player(id, player);
    }

    private void createState(Long id, Participant participant) {
        Map<String, Object> params = new HashMap<>();
        params.put("participant_id", id);
        params.put("name", participant.getStateToString());
        params.put("card_ids", ListConvertor.compressCardIds(participant.getCards()));

        stateInsertAction.executeAndReturnKey(params).longValue();
    }


    public Dealer createDealer(Dealer dealer) {
        Map<String, Object> params = new HashMap<>();
        params.put("is_player", dealer.isPlayer());
        params.put("name", dealer.getName());
        params.put("initial_betting", dealer.getInitialBetting());
        Long id = participantInsertAction.executeAndReturnKey(params).longValue();
        createState(id, dealer);

        return new Dealer(id, dealer);
    }

    public Dealer findDealerById(Long dealerId) {
        String sql = "SELECT name, card_ids FROM state WHERE participant_id = ? ";
        State state = jdbcTemplate.queryForObject(sql, stateRowMapper(), dealerId);
        return new Dealer(dealerId, state);
    }

    private RowMapper<State> stateRowMapper() {
        return (rs, rowNum) -> {
            String name = rs.getString("name");
            String cardIds = rs.getString("card_ids");
            return StateFinder.findState(name, new Cards(ListConvertor.depressCardIds(cardIds)));
        };
    }

    public Player findPlayerById(Long playerId) {
        String playerSql = "SELECT name, initial_betting FROM participant WHERE participant_id = ? ";
        String stateSql = "SELECT name, card_ids FROM state WHERE participant_id = ? ";
        State state = jdbcTemplate.queryForObject(stateSql, stateRowMapper(), playerId);
        Player player = jdbcTemplate.queryForObject(playerSql, playerRowMapper(), playerId);
        return new Player(playerId, player.getName(), player.getInitialBetting(), state);
    }

    private RowMapper<Player> playerRowMapper() {
        return (rs, rowNum) -> {
            String name = rs.getString("name");
            int bettingMoney = rs.getInt("initial_betting");
            return new Player(name, bettingMoney);
        };
    }
}
