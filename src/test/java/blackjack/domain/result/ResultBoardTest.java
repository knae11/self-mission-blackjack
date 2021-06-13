package blackjack.domain.result;

import blackjack.domain.card.Card;
import blackjack.domain.card.Cards;
import blackjack.domain.card.Denomination;
import blackjack.domain.card.Suit;
import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Participant;
import blackjack.domain.participant.Player;
import blackjack.domain.state.Blackjack;
import blackjack.domain.state.Stay;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[도메인] 결과판")
public class ResultBoardTest {

    @DisplayName("보드 초기화")
    @Test
    void createResultBoard() {
        List<Card> cards = Arrays.asList(Card.of(Suit.HEART, Denomination.TWO));
        List<Participant> participants = Arrays.asList(new Dealer(cards),
                new Player(cards),
                new Player(cards));

        ResultBoard resultBoard = new ResultBoard(participants);
        assertThat(resultBoard.getResults()).hasSize(3);
    }

    @DisplayName("결과확인 - 딜러: 10, p1: 21, p2:5")
    @Test
    void checkResults() {
        List<Card> dealerCards = Arrays.asList(Card.of(Suit.HEART, Denomination.KING));
        List<Card> p1Cards = Arrays.asList(Card.of(Suit.DIAMOND, Denomination.KING),
                Card.of(Suit.DIAMOND, Denomination.ACE));
        List<Card> p2Cards = Arrays.asList(Card.of(Suit.HEART, Denomination.THREE),
                Card.of(Suit.SPADE, Denomination.TWO));
        List<Participant> participants = Arrays.asList(
                new Dealer(new Stay(new Cards(dealerCards))),
                new Player("배럴", 1000, new Blackjack(new Cards(p1Cards))),
                new Player("bet", 1000, new Stay(new Cards(p2Cards))));

        ResultBoard resultBoard = new ResultBoard(participants);
        List<ParticipantResult> results = resultBoard.getResults();
        assertThat(results).hasSize(3);
        assertThat(results.get(0).getName()).isEqualTo("딜러");
        assertThat(results.get(1).getName()).isEqualTo("배럴");
        assertThat(results.get(2).getName()).isEqualTo("bet");
        assertThat(results.get(0).getMoneyResult()).isEqualTo(500);
        assertThat(results.get(1).getMoneyResult()).isEqualTo(1500);
        assertThat(results.get(2).getMoneyResult()).isEqualTo(0);
    }
}
