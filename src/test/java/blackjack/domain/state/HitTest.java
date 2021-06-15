package blackjack.domain.state;

import blackjack.domain.card.Card;
import blackjack.domain.card.Cards;
import blackjack.domain.card.Denomination;
import blackjack.domain.card.Suit;
import blackjack.exception.domain.card.CardCannotTakeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("[도메인] Hit 상태")
public class HitTest {
    @DisplayName("블랙잭인지 확인")
    @Test
    void isBlackjack() {
        Running hit = new Hit(new Cards());

        assertFalse(hit.isBlackjack());
    }

    @DisplayName("21 이하로 카드받기")
    @Test
    void takeCardUntil21() {
        List<Card> cardValues = Arrays.asList(
                Card.of(Suit.CLOVER, Denomination.TEN),
                Card.of(Suit.HEART, Denomination.THREE));
        Running hit = new Hit(new Cards(cardValues));

        assertThat(hit.takeCardForPlayer(true, Card.of(Suit.HEART, Denomination.EIGHT)))
                .isInstanceOf(Hit.class);
    }

    @DisplayName("21 초과로 카드받기")
    @Test
    void takeCardOver21() {
        List<Card> cardValues = Arrays.asList(
                Card.of(Suit.CLOVER, Denomination.TEN),
                Card.of(Suit.HEART, Denomination.THREE));
        Running hit = new Hit(new Cards(cardValues));

        assertThat(hit.takeCardForPlayer(true, Card.of(Suit.HEART, Denomination.NINE)))
                .isInstanceOf(Bust.class);
    }

    @DisplayName("예외 - 카드 여러장 받기")
    @Test
    void takeCardsException() {
        Running hit = new Hit(new Cards());
        List<Card> cardValues = Arrays.asList(
                Card.of(Suit.CLOVER, Denomination.TEN),
                Card.of(Suit.HEART, Denomination.THREE));

        assertThatThrownBy(() -> hit.takeCardsForPlayer(cardValues))
                .isInstanceOf(CardCannotTakeException.class);
    }

    @DisplayName("점수계산 Ace 1로 계산")
    @Test
    void calculateScore() {
        List<Card> cardValues = Arrays.asList(
                Card.of(Suit.CLOVER, Denomination.TEN),
                Card.of(Suit.HEART, Denomination.ACE));
        Running hit = new Hit(new Cards(cardValues));

        assertThat(hit.calculateScore()).isEqualTo(11);
    }
}
