package blackjack.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@JdbcTest
@DisplayName("[Dao] Room")
class BlackjackGameDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

    private BlackjackGameDao blackjackgameDao;

    @BeforeEach
    void setUp() {
        this.blackjackgameDao = new BlackjackGameDao(jdbcTemplate, dataSource);
    }

    @DisplayName("방 생성")
    @Test
    void createRoom() {
//        Deck deck = Deck.createBasic();
//        List<String> cardIds = deck.getCardIds();
//        String cardDeck = String.join("/", cardIds);
//
//        assertThat(blackjackgameDao.createRoom(cardDeck)).isNotNull();
    }
}