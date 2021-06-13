package blackjack.dao;

import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Player;
import blackjack.domain.state.Stay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@DisplayName("[Dao] Participant")
class ParticipantDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

    private ParticipantDao participantDao;

    @BeforeEach
    void setUp() {
        this.participantDao = new ParticipantDao(jdbcTemplate, dataSource);
    }

    @DisplayName("플레이어 생성")
    @Test
    void createPlayer() {
        Player player = new Player("123", 1000);

        Player createdPlayer = participantDao.createPlayer(player);

        assertThat(createdPlayer.getId()).isNotNull();
        assertThat(createdPlayer.getName()).isEqualTo("123");
        assertThat(createdPlayer.getInitialBetting()).isEqualTo(1000);
        assertTrue(createdPlayer.isPlayer());
    }

    @DisplayName("딜러 생성")
    @Test
    void createDealer() {
        Dealer dealer = new Dealer();

        Dealer createdDealer = participantDao.createDealer(dealer);

        assertThat(createdDealer.getId()).isNotNull();
        assertThat(createdDealer.getName()).isNotNull();
        assertThat(createdDealer.getInitialBetting()).isEqualTo(0);
        assertFalse(createdDealer.isPlayer());
    }
}