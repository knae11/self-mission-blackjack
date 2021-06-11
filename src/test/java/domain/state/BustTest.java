package domain.state;

import domain.card.Cards;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("[도메인] Bust 상태")
public class BustTest {
    @DisplayName("블랙잭인지 확인")
    @Test
    void isBlackjack() {
        Finished bust = new Bust(new Cards());

        assertFalse(bust.isBlackjack());
    }
}
