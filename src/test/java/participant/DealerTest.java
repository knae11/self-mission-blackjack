package participant;

import card.Card;
import card.Denomination;
import card.Suit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("[도메인] 딜러")
public class DealerTest {
    @DisplayName("딜러 생성")
    @Test
    void createDealer() {
        assertThatCode(Dealer::new).doesNotThrowAnyException();
    }

    @DisplayName("2장 받기")
    @Test
    void takeTwoCards() {
        Participant dealer = new Dealer();
        dealer.takeCards(Arrays.asList(Card.of(Suit.HEART, Denomination.ACE),
                Card.of(Suit.CLOVER, Denomination.TWO)));

        assertTrue(dealer.hasCardSizeOf(2));
    }
}
