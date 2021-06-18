package blackjack.domain.state;

import blackjack.domain.card.Cards;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("[도메인] Stay 상태")
public class StayTest {

    @DisplayName("블랙잭인지 확인")
    @Test
    void isBlackjack() {
        Finished stay = new Stay(new Cards());

        assertFalse(stay.isBlackjack());
    }

}
