package result;

import card.Card;
import card.Cards;
import card.Denomination;
import card.Suit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import participant.Dealer;
import participant.Player;
import state.Blackjack;
import state.Bust;
import state.Stay;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static result.Result.DRAW;
import static result.Result.LOSE;

@DisplayName("[도메인] 결과판정")
public class JudgeTest {
    @DisplayName("딜러 Stay, 플레이어 Stay -> 딜러 20, 플레이어 19")
    @Test
    void judge() {
        List<Card> dealerCards = Arrays.asList(
                Card.of(Suit.CLOVER, Denomination.TEN),
                Card.of(Suit.HEART, Denomination.TEN));
        List<Card> playerCards = Arrays.asList(
                Card.of(Suit.SPADE, Denomination.TEN),
                Card.of(Suit.HEART, Denomination.NINE));
        Dealer dealer = new Dealer(dealerCards);
        Player player = new Player(playerCards);

        Judge judge = new Judge(dealer);

        assertThat(judge.getResultOfPlayer(player)).isEqualTo(LOSE);
    }

    @DisplayName("딜러가 Bust 인 경우에는")
    @Nested
    class ContextDealerIsBust{
        @DisplayName("(d:22, p:23) -> 진다.")
        @Test
        void judgeDealerBustAndPlayerBust() {
            List<Card> dealerCards = Arrays.asList(
                    Card.of(Suit.CLOVER, Denomination.TEN),
                    Card.of(Suit.HEART, Denomination.TEN),
                    Card.of(Suit.HEART, Denomination.TWO));
            List<Card> playerCards = Arrays.asList(
                    Card.of(Suit.SPADE, Denomination.TEN),
                    Card.of(Suit.HEART, Denomination.NINE),
                    Card.of(Suit.HEART, Denomination.FOUR));
            Dealer dealer = new Dealer(new Bust(new Cards(dealerCards)));
            Player player = new Player(new Bust(new Cards(playerCards)));

            Judge judge = new Judge(dealer);

            assertThat(judge.getResultOfPlayer(player)).isEqualTo(LOSE);
        }

        @DisplayName("(d:22, p:21) -> 비긴다.")
        @Test
        void judgeDealerBustAndPlayerBlackjack() {
            List<Card> dealerCards = Arrays.asList(
                    Card.of(Suit.CLOVER, Denomination.TEN),
                    Card.of(Suit.HEART, Denomination.TEN),
                    Card.of(Suit.HEART, Denomination.TWO));
            List<Card> playerCards = Arrays.asList(
                    Card.of(Suit.SPADE, Denomination.TEN),
                    Card.of(Suit.HEART, Denomination.NINE),
                    Card.of(Suit.HEART, Denomination.TWO));
            Dealer dealer = new Dealer(new Bust(new Cards(dealerCards)));
            Player player = new Player(new Blackjack(new Cards(playerCards)));

            Judge judge = new Judge(dealer);

            assertThat(judge.getResultOfPlayer(player)).isEqualTo(DRAW);
        }

        @DisplayName("(d:22, p:20) -> 비긴다.")
        @Test
        void judgeDealerBustAndPlayerStay() {
            List<Card> dealerCards = Arrays.asList(
                    Card.of(Suit.CLOVER, Denomination.TEN),
                    Card.of(Suit.HEART, Denomination.TEN),
                    Card.of(Suit.HEART, Denomination.TWO));
            List<Card> playerCards = Arrays.asList(
                    Card.of(Suit.SPADE, Denomination.TEN),
                    Card.of(Suit.HEART, Denomination.NINE),
                    Card.of(Suit.HEART, Denomination.ACE));
            Dealer dealer = new Dealer(new Bust(new Cards(dealerCards)));
            Player player = new Player(new Stay(new Cards(playerCards)));

            Judge judge = new Judge(dealer);

            assertThat(judge.getResultOfPlayer(player)).isEqualTo(DRAW);
        }
    }


}
