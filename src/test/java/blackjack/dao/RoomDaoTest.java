package blackjack.dao;

import blackjack.domain.card.Deck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@DisplayName("[Dao] Room")
class RoomDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

    private RoomDao roomDao;

    @BeforeEach
    void setUp(){
        this.roomDao = new RoomDao(jdbcTemplate, dataSource);
    }

    @DisplayName("방 생성")
    @Test
    void createRoom() {
        Deck deck = Deck.createBasic();
        List<String> cardIds = deck.getCardIds();
        String cardDeck = String.join("/", cardIds);

        assertThat(roomDao.createRoom(cardDeck)).isNotNull();
    }
}