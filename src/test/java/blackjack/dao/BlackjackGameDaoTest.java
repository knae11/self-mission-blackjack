package blackjack.dao;

import blackjack.domain.BlackjackGame;
import blackjack.domain.participant.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@DisplayName("[Dao] Game")
class BlackjackGameDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private BlackjackGameDao blackjackgameDao;

    @BeforeEach
    void setUp() {
        this.blackjackgameDao = new BlackjackGameDao(jdbcTemplate);
    }


    @DisplayName("게임 생성")
    @Test
    void create() {
        // given
        List<Player> players = Arrays.asList(
                new Player("better", 1000),
                new Player("배럴", 2000));
        BlackjackGame game = BlackjackGame.createInitial(players);

        // when
        BlackjackGame createdGame = blackjackgameDao.create(game);

        // then
        assertThat(createdGame.getId()).isNotNull();
        assertThat(createdGame.getDealer().getCards()).hasSize(2);
        assertThat(createdGame.getPlayers()).hasSize(2);
    }

    @DisplayName("게임정보 가져오기")
    @Test
    void name() {
        // given
        List<Player> players = Arrays.asList(
                new Player("better", 1000),
                new Player("배럴", 2000));
        BlackjackGame game = BlackjackGame.createInitial(players);
        BlackjackGame createdGame = blackjackgameDao.create(game);

        // when
        BlackjackGame foundGame = blackjackgameDao.findByGameId(createdGame.getId());

        // then
        assertThat(foundGame.getId()).isEqualTo(createdGame.getId());
        assertThat(foundGame.getDealer().getCards()).containsExactlyInAnyOrderElementsOf(createdGame.getDealer().getCards());
        assertThat(foundGame.getPlayers().get(0).getCards())
                .containsExactlyInAnyOrderElementsOf(createdGame.getPlayers().get(0).getCards());
    }
}