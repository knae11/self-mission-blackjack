package blackjack.dao;

import blackjack.domain.card.Card;
import blackjack.domain.card.Deck;
import blackjack.domain.card.Denomination;
import blackjack.domain.card.Suit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@DisplayName("[Dao] Deck")
class DeckDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

    private DeckDao deckDao;

    @BeforeEach
    void setUp() {
        this.deckDao = new DeckDao(jdbcTemplate, dataSource);
    }

    @DisplayName("덱 생성")
    @Test
    void create() {
        Deck deck = Deck.listOf(Arrays.asList(Card.of(Suit.DIAMOND, Denomination.TWO),
                Card.of(Suit.HEART, Denomination.THREE)));

        Deck createdDeck = deckDao.create(deck);

        assertThat(createdDeck.getId()).isNotNull();
        assertThat(createdDeck.getCards()).containsExactlyElementsOf(deck.getCards());
    }

}