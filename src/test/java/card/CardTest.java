package card;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("[도메인] 카드")
public class CardTest {
    @DisplayName("카드 생성")
    @Test
    void createCard() {
        assertThatCode(() -> Card.of(Suit.HEART, Denomination.ACE)).doesNotThrowAnyException();
    }

    @DisplayName("52장의 카드는 캐싱")
    @Test
    void cachedCard() {
        Card h1 = Card.of(Suit.HEART, Denomination.ACE);

        assertThat(h1).isSameAs(Card.of(Suit.HEART, Denomination.ACE));
    }

}
