package blackjack.dao;

import blackjack.application.StateFinder;
import blackjack.domain.BlackjackGame;
import blackjack.domain.card.Card;
import blackjack.domain.card.Cards;
import blackjack.domain.card.Deck;
import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Player;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BlackjackGameDao {
    private final JdbcTemplate jdbcTemplate;

    public BlackjackGameDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long findDealerId(Long gameId) {
        String sql = "SELECT (dealer_id) FROM blackjackgame WHERE game_id = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, gameId);
    }

    public String findPlayerIds(Long gameId) {
        String sql = "SELECT (player_ids) FROM blackjackgame WHERE game_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, gameId);
    }

    public Long findDeckId(Long gameId) {
        String sql = "SELECT (deck_id) FROM blackjackgame WHERE game_id = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, gameId);
    }

    public BlackjackGame create(BlackjackGame blackjackGame) {
        String sql = "INSERT INTO blackjackgame (is_playing) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"game_id"});
            ps.setBoolean(1, true);
            return ps;
        }, keyHolder);

        Long gameId = (long) keyHolder.getKey();

        Dealer createdDealer = createDealer(blackjackGame.getDealer(), gameId);
        List<Player> createdPlayers = createPlayer(blackjackGame.getPlayers(), gameId);
        createDeck(blackjackGame.getDeck(), gameId);

        return BlackjackGame.create(gameId, createdDealer, createdPlayers, blackjackGame.getDeck());
    }

    private void createDeck(Deck deck, Long gameId) {
        String sql = "INSERT INTO deck_card (card, game_id) VALUES (?,?)";

        deck.getCards()
                .forEach(card ->
                        jdbcTemplate.update(sql, card.getCardId(), gameId));
    }

    private List<Player> createPlayer(List<Player> players, Long gameId) {
        String sql = "INSERT INTO player (game_id, state, name, initial_betting) VALUES (?,?,?,?)";

        List<Player> createdPlayers = players.stream()
                .map(player -> {
                    KeyHolder keyHolder = new GeneratedKeyHolder();
                    jdbcTemplate.update(con -> {
                        PreparedStatement ps = con.prepareStatement(sql, new String[]{"player_id"});
                        ps.setLong(1, gameId);
                        ps.setString(2, player.getStateToString());
                        ps.setString(3, player.getName());
                        ps.setInt(4, player.getInitialBetting());
                        return ps;
                    }, keyHolder);

                    Long playerId = (long) keyHolder.getKey();
                    Player createdPlayer = Player.create(playerId, player);
                    createPlayerCard(createdPlayer);

                    return createdPlayer;
                })
                .collect(Collectors.toList());

        return createdPlayers;
    }

    private void createPlayerCard(Player player) {
        String sql = "INSERT INTO player_card (card, player_id) VALUES (?,?)";

        List<Card> cards = player.getCards();
        cards.forEach(card ->
                jdbcTemplate.update(sql, card.getCardId(), player.getId()));

    }

    private Dealer createDealer(Dealer dealer, Long gameId) {
        String sql = "INSERT INTO dealer (game_id, state) VALUES (?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"dealer_id"});
            ps.setLong(1, gameId);
            ps.setString(2, dealer.getStateToString());
            return ps;
        }, keyHolder);

        Long dealerId = (long) keyHolder.getKey();
        createDealerCard(dealer.getCards(), dealerId);
        Dealer createdDealer = Dealer.create(dealerId, dealer);

        return createdDealer;
    }

    private void createDealerCard(List<Card> cards, Long dealerId) {
        String sql = "INSERT INTO dealer_card (card, dealer_id) VALUES (?,?)";

        cards.forEach(card ->
                jdbcTemplate.update(sql, new Object[]{card.getCardId(), dealerId}));

    }

    public BlackjackGame findByGameId(Long gameId) {
        Dealer dealer = findDealer(gameId);
        List<Player> players = findPlayers(gameId);

        return BlackjackGame.create(gameId, dealer, players);
    }

    private List<Player> findPlayers(Long gameId) {
        String playerSql = "SELECT player_id, state, name, initial_betting FROM player WHERE game_id = ?";
        return jdbcTemplate.query(playerSql, playerRowMapper(), gameId);
    }

    private Dealer findDealer(Long gameId) {
        String dealerSql = "SELECT dealer_id, state FROM dealer WHERE game_id = ?";
        return jdbcTemplate.queryForObject(dealerSql, dealerRowMapper(), gameId);
    }

    private RowMapper<Dealer> dealerRowMapper() {
        return (rs, rowNum) -> {
            long dealerId = rs.getLong("dealer_id");
            String stateName = rs.getString("state");

            String cardsSql = "SELECT card FROM dealer_card WHERE dealer_id = ? ORDER BY card_id";
            List<Card> cards = jdbcTemplate.queryForList(cardsSql, Card.class, dealerId);

            return new Dealer(dealerId, StateFinder.findState(stateName, new Cards(cards)));
        };
    }

    private RowMapper<Player> playerRowMapper() {
        return (rs, rowNum) -> {
            long playerId = rs.getLong("player_id");
            String stateName = rs.getString("state");
            String name = rs.getString("name");
            int initialBetting = rs.getInt("initial_betting");

            String cardsSql = "SELECT card FROM player_card WHERE player_id = ? ORDER BY card_id";
            List<Card> cards = jdbcTemplate.queryForList(cardsSql, Card.class, playerId);

            return new Player(playerId, name, initialBetting, StateFinder.findState(stateName, new Cards(cards)));
        };

    }

    public BlackjackGame findForAPlayerTurn(Long gameId, Long playerId) {
        List<Card> deckCards = findDeck(gameId);

        Player player = findAPlayer(gameId, playerId);

        return BlackjackGame.create(gameId, player, Deck.listOf(deckCards));

    }

    private List<Card> findDeck(Long gameId) {
        String deckSql = "SELECT card FROM deck_card WHERE game_id = ? ORDER BY card_id";
        return jdbcTemplate.queryForList(deckSql, Card.class, gameId);
    }

    private Player findAPlayer(Long gameId, Long playerId) {
        String playerSql = "SELECT player_id, state, name, initial_betting FROM player WHERE (game_id = ? AND player_id = ?)";
        return jdbcTemplate.queryForObject(playerSql, playerRowMapper(), new Object[]{gameId, playerId});
    }

    public BlackjackGame findForDealerTurn(Long gameId) {
        Dealer dealer = findDealer(gameId);
        List<Card> deckCards = findDeck(gameId);
        return BlackjackGame.create(gameId, dealer, Deck.listOf(deckCards));
    }
}
