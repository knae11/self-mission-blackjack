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
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[도메인] 결과판")
public class ResultBoardTest {

    @DisplayName("보드 초기화")
    @Test
    void createResultBoard() {
        List<Card> cards = Arrays.asList(Card.of(Suit.HEART, Denomination.TWO));
        Dealer dealer = new Dealer(cards);
        List<Player> players = Arrays.asList(
                new Player(cards),
                new Player(cards));

        ResultBoard resultBoard = new ResultBoard(dealer, players);
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

        Dealer dealer = new Dealer(new Stay(new Cards(dealerCards)));
        Player p1 = new Player("배럴", 1000, new Blackjack(new Cards(p1Cards)));
        Player p2 = new Player("bet", 1000, new Stay(new Cards(p2Cards)));
        List<Player> players = Arrays.asList(p1, p2);

        ResultBoard resultBoard = new ResultBoard(dealer, players);
        Map<Participant, ParticipantResult> results = resultBoard.getResults();
        assertThat(results).hasSize(3);
        assertThat(results.get(dealer).getName()).isEqualTo("딜러");
        assertThat(results.get(p1).getName()).isEqualTo("배럴");
        assertThat(results.get(p2).getName()).isEqualTo("bet");
        assertThat(results.get(dealer).getMoneyResult()).isEqualTo(500);
        assertThat(results.get(p1).getMoneyResult()).isEqualTo(1500);
        assertThat(results.get(p2).getMoneyResult()).isEqualTo(0);
    }
}
