package card;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

public class CardTest {
    @DisplayName("카드 생성")
    @Test
    void createCard() {
        assertThatCode(() -> new Card(Suit.HEART, Denomination.ACE)).doesNotThrowAnyException();
    }
}
