package participant;

import card.Card;
import card.Cards;
import card.Denomination;
import card.Suit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DisplayName("[도메인] 플레이어")
public class PlayerTest {
    @DisplayName("플레이어 생성")
    @Test
    void createPlayer() {
        assertThatCode(() -> new Player("better", 1000)).doesNotThrowAnyException();
    }

    @DisplayName("2장 카드 받기")
    @Test
    void takeTwoCards() {
        Participant player = new Player();
        player.takeCards(Arrays.asList(Card.of(Suit.HEART, Denomination.ACE),
                Card.of(Suit.CLOVER, Denomination.TWO)));

        assertTrue(player.hasCardSizeOf(2));
    }

    @DisplayName("1장 카드 받기")
    @Test
    void takeCard() {
        Participant player = new Player();
        player.takeCard(Card.of(Suit.HEART, Denomination.TWO));

        assertTrue(player.hasCardSizeOf(1));
    }
}
