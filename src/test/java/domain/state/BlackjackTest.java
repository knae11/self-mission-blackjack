package domain.state;

import domain.card.Card;
import domain.card.Cards;
import domain.card.Denomination;
import domain.card.Suit;
import exception.card.CardCannotTakeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("[도메인] 블랙잭상태")
public class BlackjackTest {
    @DisplayName("블랙잭 인지 확인")
    @Test
    void isBlackjack() {
        Finished blackjack = new Blackjack(new Cards());

        assertTrue(blackjack.isBlackjack());
    }

    @DisplayName("예외 - 카드 받기")
    @Test
    void takeCardException() {
        Finished blackjack = new Blackjack(new Cards());

        assertThatThrownBy(() -> blackjack.takeCard(Card.of(Suit.CLOVER, Denomination.TWO)))
                .isInstanceOf(CardCannotTakeException.class);
    }

    @DisplayName("카드 점수 계산")
    @Test
    void calculateScore() {
        Cards cards = new Cards(Arrays.asList(Card.of(Suit.HEART, Denomination.TEN),
                Card.of(Suit.CLOVER, Denomination.ACE)));
        Finished blackjack = new Blackjack(cards);

        assertThat(blackjack.calculateScore()).isEqualTo(21);
    }
}
