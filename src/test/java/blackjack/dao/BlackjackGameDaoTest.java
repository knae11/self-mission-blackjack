package blackjack.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@DisplayName("[Dao] Game")
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

    @DisplayName("게임 생성")
    @Test
    void create() {
        assertThat(blackjackgameDao.create(1L, "2,3,4", 1L)).isNotNull();
    }
}