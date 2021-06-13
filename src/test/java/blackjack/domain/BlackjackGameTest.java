package blackjack.domain;

import blackjack.domain.card.*;
import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Player;
import blackjack.domain.state.Hit;
import blackjack.domain.state.State;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("[도메인] 블랙잭게임")
public class BlackjackGameTest {

    @DisplayName("게임 시작")
    @Test
    void initGame() {
        // given
        Dealer dealer = new Dealer();
        List<Player> players = Arrays.asList(
                new Player("1", 1000),
                new Player("2", 2000)
        );
        Deck deck = Deck.createBasic();
        BlackjackGame blackjackGame = new BlackjackGame(dealer, players, deck);

        // when
        blackjackGame.initGame();

        // then
        assertTrue(dealer.hasCardSizeOf(2));
        players.forEach(player -> assertTrue(player.hasCardSizeOf(2)));
    }

    @DisplayName("1명의 플레이어가 카드를 받기 가능한 경우인지 확인")
    @Test
    void checkIfAbleToTakeCardOfOnePlayer() {
        // given
        List<Card> p1Cards = Arrays.asList(Card.of(Suit.HEART, Denomination.TWO),
                Card.of(Suit.DIAMOND, Denomination.TWO));
        Dealer dealer = new Dealer();
        Player player = new Player("1", 1000, new Hit(new Cards(p1Cards)));
        Deck deck = Deck.listOf(Collections.singletonList(Card.of(Suit.CLOVER, Denomination.THREE)));
        BlackjackGame blackjackGame = new BlackjackGame(player);

        // when
        boolean ableToTake = blackjackGame.isAbleToTakeCardOf(player);

        // then
        assertTrue(ableToTake);
    }

    @DisplayName("1명의 플레이어가 카드를 받음")
    @Test
    void takeCardOfOnePlayer() {
        // given
        List<Card> p1Cards = Arrays.asList(Card.of(Suit.HEART, Denomination.TWO),
                Card.of(Suit.DIAMOND, Denomination.TWO));
        Dealer dealer = new Dealer();
        Player player = new Player("1", 1000, new Hit(new Cards(p1Cards)));
        Deck deck = Deck.listOf(Collections.singletonList(Card.of(Suit.CLOVER, Denomination.THREE)));
        BlackjackGame blackjackGame = new BlackjackGame(player, deck);

        // when
        State state = blackjackGame.takeTurnOf(true, player);

        // then
        assertTrue(state.isRunning());
        assertTrue(player.hasCardSizeOf(3));
        assertThat(player.calculateScore()).isEqualTo(7);
    }

    @DisplayName("1명의 플레이어가 카드를 받지 않음")
    @Test
    void notTakeCardOfOnePlayer() {
        // given
        List<Card> p1Cards = Arrays.asList(Card.of(Suit.HEART, Denomination.TWO),
                Card.of(Suit.DIAMOND, Denomination.TWO));
        Dealer dealer = new Dealer();
        Player player = new Player("1", 1000, new Hit(new Cards(p1Cards)));
        Deck deck = Deck.listOf(Collections.singletonList(Card.of(Suit.CLOVER, Denomination.THREE)));
        BlackjackGame blackjackGame = new BlackjackGame(player, deck);

        // when
        State state = blackjackGame.takeTurnOf(false, player);

        // then
        assertFalse(state.isRunning());
        assertTrue(player.hasCardSizeOf(2));
        assertThat(player.calculateScore()).isEqualTo(4);
    }
}
