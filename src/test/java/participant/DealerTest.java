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

@DisplayName("[도메인] 딜러")
public class DealerTest {
    @DisplayName("딜러 생성")
    @Test
    void createDealer() {
        assertThatCode(Dealer::new).doesNotThrowAnyException();
    }

    @DisplayName("2장 카드 받기")
    @Test
    void takeTwoCards() {
        Participant dealer = new Dealer();
        dealer.takeCards(Arrays.asList(Card.of(Suit.HEART, Denomination.ACE),
                Card.of(Suit.CLOVER, Denomination.TWO)));

        assertTrue(dealer.hasCardSizeOf(2));
    }

    @DisplayName("1장 카드 받기")
    @Test
    void takeCard() {
        Participant dealer = new Dealer();
        dealer.takeCard(Card.of(Suit.HEART, Denomination.TWO));

        assertTrue(dealer.hasCardSizeOf(1));
    }


    @DisplayName("카드합계 16, 받을 수 있는지 조건 확인")
    @Test
    void isAbleToTakeWhen21() {
        Participant dealer = new Dealer();
        dealer.takeCards(Arrays.asList(Card.of(Suit.HEART, Denomination.TEN),
                Card.of(Suit.CLOVER, Denomination.SIX)));

        assertTrue(dealer.isAbleToTake());
    }

    @DisplayName("카드합계 17, 받을 수 있는지 조건 확인")
    @Test
    void isAbleToTakeWhen22() {
        Participant dealer = new Dealer();
        dealer.takeCards(Arrays.asList(Card.of(Suit.HEART, Denomination.TEN),
                Card.of(Suit.CLOVER, Denomination.SEVEN)));

        assertFalse(dealer.isAbleToTake());
    }
}
