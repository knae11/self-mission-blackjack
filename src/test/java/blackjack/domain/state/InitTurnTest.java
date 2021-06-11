package blackjack.domain.state;

import blackjack.domain.card.Card;
import blackjack.domain.card.Denomination;
import blackjack.domain.card.Suit;
import blackjack.exception.card.CardCannotTakeException;
import blackjack.exception.card.EmptyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("[도메인] 시작 턴 상태")
public class InitTurnTest {
    @DisplayName("블랙잭인지 확인")
    @Test
    void isBlackjack() {
        Running initTurn = new InitTurn();

        assertFalse(initTurn.isBlackjack());
    }


    @DisplayName("21 미만으로 카드받기")
    @Test
    void takeCardsUnder21() {
        Running initTurn = new InitTurn();
        List<Card> cardValues = Arrays.asList(
                Card.of(Suit.CLOVER, Denomination.TEN),
                Card.of(Suit.HEART, Denomination.KING));


        assertThat(initTurn.takeCards(cardValues))
                .isInstanceOf(Hit.class);
    }

    @DisplayName("블랙잭으로 카드받기")
    @Test
    void takeCards21() {
        Running initTurn = new InitTurn();
        List<Card> cardValues = Arrays.asList(
                Card.of(Suit.CLOVER, Denomination.TEN),
                Card.of(Suit.HEART, Denomination.ACE));

        assertThat(initTurn.takeCards(cardValues))
                .isInstanceOf(Blackjack.class);
    }

    @DisplayName("예외 - 카드 1장 받기")
    @Test
    void takeCardsException() {
        Running initTurn = new InitTurn();

        assertThatThrownBy(() -> initTurn.takeCard(Card.of(Suit.CLOVER, Denomination.TEN)))
                .isInstanceOf(CardCannotTakeException.class);
    }

    @DisplayName("예외 - 점수계산")
    @Test
    void calculateScore() {
        Running initTurn = new InitTurn();

        assertThatThrownBy(initTurn::calculateScore)
                .isInstanceOf(EmptyException.class);
    }
}
