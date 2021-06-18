package blackjack.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@DisplayName("[Dao] - PlayerDao")
class PlayerDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private PlayerDao playerDao;

    @BeforeEach
    void setUp() {
        this.playerDao = new PlayerDao(jdbcTemplate);
    }

    @DisplayName("플레이어 찾기")
    @Test
    void findByIds() {

    }
}