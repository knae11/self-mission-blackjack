package blackjack.domain.card;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("[도메인] Cards")
class CardsTest {

    @DisplayName("사이즈 확인")
    @Test
    void isSizeOf() {
        List<Card> cardValues = Arrays.asList(Card.of(Suit.CLOVER, Denomination.ACE),
                Card.of(Suit.HEART, Denomination.ACE));
        Cards cards = new Cards(cardValues);

        assertTrue(cards.isSizeOf(2));
    }

    @DisplayName("카드 추가")
    @Test
    void add() {
        Cards cards = new Cards();

        cards.add(Card.of(Suit.HEART, Denomination.TWO));
        cards.add(Arrays.asList(Card.of(Suit.CLOVER, Denomination.TWO),
                Card.of(Suit.HEART, Denomination.THREE)));

        assertTrue(cards.isSizeOf(3));
    }

    @DisplayName("Ace 1로 계산")
    @Test
    void calculateScoreAceAsOne() {
        List<Card> cardValues = Arrays.asList(Card.of(Suit.CLOVER, Denomination.ACE),
                Card.of(Suit.HEART, Denomination.ACE));
        Cards cards = new Cards(cardValues);

        assertThat(cards.calculateScoreAceAsOne()).isEqualTo(2);
    }

    @DisplayName("Ace 21 이 넘지 않는 경우 11로 계산")
    @Test
    void calculateFinalScore21() {
        List<Card> cardValues = Arrays.asList(Card.of(Suit.CLOVER, Denomination.ACE),
                Card.of(Suit.HEART, Denomination.TEN));
        Cards cards = new Cards(cardValues);

        assertThat(cards.calculateFinalScore()).isEqualTo(21);
    }

    @DisplayName("Ace 21 이 넘는 경우, 1 또는 11로 계산")
    @Test
    void calculateFinalScore22() {
        List<Card> cardValues = Arrays.asList(Card.of(Suit.CLOVER, Denomination.ACE),
                Card.of(Suit.HEART, Denomination.ACE));
        Cards cards = new Cards(cardValues);

        assertThat(cards.calculateFinalScore()).isEqualTo(12);
    }
}