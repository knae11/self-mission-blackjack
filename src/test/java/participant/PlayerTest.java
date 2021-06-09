package participant;

import card.Card;
import card.Denomination;
import card.Suit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
        player.takeCards(Arrays.asList(
                Card.of(Suit.HEART, Denomination.TWO),
                Card.of(Suit.CLOVER, Denomination.TWO)
        ));

        player.takeCard(Card.of(Suit.HEART, Denomination.TWO));

        assertTrue(player.hasCardSizeOf(3));
    }

    @DisplayName("카드합계 21, 받을 수 있는지 조건 확인")
    @Test
    void isAbleToTakeWhen21() {
        Participant player = new Player();
        player.takeCards(Arrays.asList(Card.of(Suit.HEART, Denomination.TEN),
                Card.of(Suit.CLOVER, Denomination.TWO),
                Card.of(Suit.CLOVER, Denomination.NINE)));

        assertTrue(player.isAbleToTake());
    }

    @DisplayName("카드합계 22, 받을 수 있는지 조건 확인")
    @Test
    void isAbleToTakeWhen22() {
        Participant player = new Player();
        player.takeCards(Arrays.asList(Card.of(Suit.HEART, Denomination.TEN),
                Card.of(Suit.CLOVER, Denomination.THREE),
                Card.of(Suit.CLOVER, Denomination.NINE)));

        assertFalse(player.isAbleToTake());
    }

    @DisplayName("처음 상태 확인")
    @Test
    void initialState() {
        Player player = new Player();

        assertTrue(player.isRunning());
    }

    @DisplayName("블랙잭 상태 확인")
    @Test
    void takeCard21() {
        Player player = new Player();
        player.takeCards(Arrays.asList(Card.of(Suit.HEART, Denomination.ACE),
                Card.of(Suit.CLOVER, Denomination.TEN)));

        assertFalse(player.isRunning());
    }
}
