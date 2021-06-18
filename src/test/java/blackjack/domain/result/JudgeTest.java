package blackjack.domain.result;

import blackjack.domain.card.Card;
import blackjack.domain.card.Cards;
import blackjack.domain.card.Denomination;
import blackjack.domain.card.Suit;
import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Player;
import blackjack.domain.state.Blackjack;
import blackjack.domain.state.Bust;
import blackjack.domain.state.Stay;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static blackjack.domain.result.Result.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[도메인] 결과판정")
public class JudgeTest {

    @DisplayName("딜러와 플레이어가 모두 Bust가 아닌 경우에는 ")
    @Nested
    class ContextBothNotBust {

        @DisplayName("딜러 Stay, 플레이어 Stay(d:20, p:19) -> 진다.")
        @Test
        void judgeBothStay() {
            List<Card> dealerCards = Arrays.asList(
                    Card.of(Suit.CLOVER, Denomination.TEN),
                    Card.of(Suit.HEART, Denomination.TEN));
            List<Card> playerCards = Arrays.asList(
                    Card.of(Suit.SPADE, Denomination.TEN),
                    Card.of(Suit.HEART, Denomination.NINE));
            Dealer dealer = new Dealer(new Stay(new Cards(dealerCards)));
            Player player = new Player(new Stay(new Cards(playerCards)));

            Judge judge = new Judge(dealer);

            assertThat(judge.getResultOfPlayer(player)).isEqualTo(LOSE);
        }

        @DisplayName("딜러 Blackjack, 플레이어 Stay(d:21, p:19) -> 진다.")
        @Test
        void judge() {
            List<Card> dealerCards = Arrays.asList(
                    Card.of(Suit.CLOVER, Denomination.TEN),
                    Card.of(Suit.HEART, Denomination.ACE));
            List<Card> playerCards = Arrays.asList(
                    Card.of(Suit.SPADE, Denomination.TEN),
                    Card.of(Suit.HEART, Denomination.NINE));
            Dealer dealer = new Dealer(new Stay(new Cards(dealerCards)));
            Player player = new Player(new Stay(new Cards(playerCards)));

            Judge judge = new Judge(dealer);

            assertThat(judge.getResultOfPlayer(player)).isEqualTo(LOSE);
        }

        @DisplayName("딜러 Stay, 플레이어 Blackjack (d:20, p:21) -> 이긴다.")
        @Test
        void judgeDealerStayAndPlayerBlackjack() {
            List<Card> dealerCards = Arrays.asList(
                    Card.of(Suit.CLOVER, Denomination.TEN),
                    Card.of(Suit.HEART, Denomination.TEN));
            List<Card> playerCards = Arrays.asList(
                    Card.of(Suit.SPADE, Denomination.TEN),
                    Card.of(Suit.HEART, Denomination.ACE));
            Dealer dealer = new Dealer(new Stay(new Cards(dealerCards)));
            Player player = new Player(new Stay(new Cards(playerCards)));

            Judge judge = new Judge(dealer);

            assertThat(judge.getResultOfPlayer(player)).isEqualTo(WIN);
        }

        @DisplayName("딜러 Blackjack, 플레이어 Blackjack (d:21, p:21) -> 비긴다.")
        @Test
        void judgeBothBlackjack() {
            List<Card> dealerCards = Arrays.asList(
                    Card.of(Suit.CLOVER, Denomination.TEN),
                    Card.of(Suit.DIAMOND, Denomination.ACE));
            List<Card> playerCards = Arrays.asList(
                    Card.of(Suit.SPADE, Denomination.TEN),
                    Card.of(Suit.HEART, Denomination.ACE));
            Dealer dealer = new Dealer(new Stay(new Cards(dealerCards)));
            Player player = new Player(new Stay(new Cards(playerCards)));

            Judge judge = new Judge(dealer);

            assertThat(judge.getResultOfPlayer(player)).isEqualTo(DRAW);
        }
    }

    @DisplayName("딜러가 Bust 인 경우에는")
    @Nested
    class ContextDealerIsBust {
        @DisplayName("딜러 Bust, 플레이어 Bust(d:22, p:23) -> 진다.")
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

        @DisplayName("딜러 Bust, 플레이어 Blackjack(d:22, p:21) -> 비긴다.")
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

        @DisplayName("딜러 Bust, 플레이어 Stay(d:22, p:20) -> 비긴다.")
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

    @DisplayName("플레이어가 Bust 인 경우에는")
    @Nested
    class ContextPlayerBust {
        @DisplayName("딜러 Stay, 플레이어 Bust(d:20, p:22) -> 진다.")
        @Test
        void judgeDealerStayAndPlayerBust() {
            List<Card> dealerCards = Arrays.asList(
                    Card.of(Suit.CLOVER, Denomination.TEN),
                    Card.of(Suit.HEART, Denomination.TEN));
            List<Card> playerCards = Arrays.asList(
                    Card.of(Suit.SPADE, Denomination.TEN),
                    Card.of(Suit.DIAMOND, Denomination.TEN),
                    Card.of(Suit.HEART, Denomination.TWO));
            Dealer dealer = new Dealer(new Stay(new Cards(dealerCards)));
            Player player = new Player(new Bust(new Cards(playerCards)));

            Judge judge = new Judge(dealer);

            assertThat(judge.getResultOfPlayer(player)).isEqualTo(LOSE);
        }

        @DisplayName("딜러 Blackjack, 플레이어 Bust(d:21, p:22) -> 진다.")
        @Test
        void judgeDealerBlackjackAndPlayerBust() {
            List<Card> dealerCards = Arrays.asList(
                    Card.of(Suit.CLOVER, Denomination.TEN),
                    Card.of(Suit.HEART, Denomination.ACE));
            List<Card> playerCards = Arrays.asList(
                    Card.of(Suit.SPADE, Denomination.TEN),
                    Card.of(Suit.DIAMOND, Denomination.TEN),
                    Card.of(Suit.HEART, Denomination.TWO));
            Dealer dealer = new Dealer(new Blackjack(new Cards(dealerCards)));
            Player player = new Player(new Bust(new Cards(playerCards)));

            Judge judge = new Judge(dealer);

            assertThat(judge.getResultOfPlayer(player)).isEqualTo(LOSE);
        }
    }

}
