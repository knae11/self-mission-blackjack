package blackjack.domain;

import blackjack.domain.card.*;
import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Participant;
import blackjack.domain.participant.Player;
import blackjack.domain.result.ParticipantResult;
import blackjack.domain.state.Blackjack;
import blackjack.domain.state.Hit;
import blackjack.domain.state.State;
import blackjack.domain.state.Stay;
import blackjack.exception.domain.GameNotEndException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    void isPlayerAbleToTakeCard() {
        // given
        List<Card> p1Cards = Arrays.asList(Card.of(Suit.HEART, Denomination.TWO),
                Card.of(Suit.DIAMOND, Denomination.TWO));
        Player player = new Player("1", 1000, new Hit(new Cards(p1Cards)));
        BlackjackGame blackjackGame = new BlackjackGame(player);

        // when
        boolean isAbleToTake = blackjackGame.isAbleToTakeCardOf(player);

        // then
        assertTrue(isAbleToTake);
        assertTrue(player.isRunning());
    }

    @DisplayName("1명의 플레이어가 카드를 받을 수 없는 경우인지 확인")
    @Test
    void isPlayerNotAbleToTakeCard() {
        // given
        List<Card> p1Cards = Arrays.asList(Card.of(Suit.HEART, Denomination.TEN),
                Card.of(Suit.DIAMOND, Denomination.ACE));
        Player player = new Player("1", 1000, new Blackjack(new Cards(p1Cards)));
        BlackjackGame blackjackGame = new BlackjackGame(player);

        // when
        boolean isAbleToTake = blackjackGame.isAbleToTakeCardOf(player);

        // then
        assertFalse(isAbleToTake);
        assertFalse(player.isRunning());
    }

    @DisplayName("1명의 플레이어가 카드를 받음")
    @Test
    void takeCardOfOnePlayer() {
        // given
        List<Card> p1Cards = Arrays.asList(Card.of(Suit.HEART, Denomination.TWO),
                Card.of(Suit.DIAMOND, Denomination.TWO));
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

    @DisplayName("딜러가 카드를 받을 수 있는 경우")
    @Test
    void isDealerAbleToTakeCard() {
        // given
        Dealer dealer = new Dealer();
        Deck deck = Deck.listOf(Arrays.asList(Card.of(Suit.HEART, Denomination.TEN),
                Card.of(Suit.DIAMOND, Denomination.SIX)));
        BlackjackGame blackjackGame = new BlackjackGame(dealer, deck);
        blackjackGame.initGame();

        // when
        boolean isAbleToTake = blackjackGame.isAbleToTakeCardOf(dealer);

        // then
        assertTrue(isAbleToTake);
        assertTrue(dealer.isRunning());
    }

    @DisplayName("딜러가 카드를 받을 수 없는 경우")
    @Test
    void isDealerNotAbleToTakeCard() {
        // given
        Dealer dealer = new Dealer();
        Deck deck = Deck.listOf(Arrays.asList(Card.of(Suit.HEART, Denomination.TEN),
                Card.of(Suit.DIAMOND, Denomination.SEVEN)));
        BlackjackGame blackjackGame = new BlackjackGame(dealer, deck);
        blackjackGame.initGame();

        // when
        boolean isAbleToTake = blackjackGame.isAbleToTakeCardOf(dealer);

        // then
        assertFalse(isAbleToTake);
        assertFalse(dealer.isRunning());
    }

    @DisplayName("딜러가 카드가 16이하면 카드 1장 받기")
    @Test
    void takeCardOfDealer() {
        // given
        List<Card> cards = Arrays.asList(Card.of(Suit.HEART, Denomination.TEN),
                Card.of(Suit.DIAMOND, Denomination.SIX));
        Dealer dealer = new Dealer(new Hit(new Cards(cards)));
        Deck deck = Deck.listOf(Collections.singletonList(Card.of(Suit.DIAMOND, Denomination.TWO)));
        BlackjackGame blackjackGame = new BlackjackGame(dealer, deck);

        // when
        boolean isAbleToTake = blackjackGame.isAbleToTakeCardOf(dealer);
        if (isAbleToTake) {
            blackjackGame.takeTurnOf(dealer);
        }

        // then
        assertTrue(isAbleToTake);
        assertFalse(dealer.isRunning());
        assertThat(dealer.calculateScore()).isEqualTo(18);
    }

    @DisplayName("결과 계산")
    @Test
    void result() {
        // given
        List<Card> blackjackCards = Arrays.asList(Card.of(Suit.DIAMOND, Denomination.TEN),
                Card.of(Suit.HEART, Denomination.ACE));
        List<Card> stayCards = Arrays.asList(Card.of(Suit.HEART, Denomination.TEN),
                Card.of(Suit.DIAMOND, Denomination.NINE));
        Dealer dealer = new Dealer(new Blackjack(new Cards(blackjackCards)));
        List<Player> players = Arrays.asList(
                new Player("하나", 1000, new Blackjack(new Cards(blackjackCards))),
                new Player("둘", 2000, new Stay(new Cards(stayCards))));
        BlackjackGame blackjackGame = new BlackjackGame(dealer, players);

        // when
        Map<Participant, ParticipantResult> result = blackjackGame.getResult();

        // then
        assertThat(result).hasSize(3);
    }

    @DisplayName("예외 - 모두 게임이 끝나지 않은 상태에서 결과 계산")
    @Test
    void resultException() {
        // given
        List<Card> blackjackCards = Arrays.asList(Card.of(Suit.DIAMOND, Denomination.TEN),
                Card.of(Suit.HEART, Denomination.ACE));
        List<Card> cards = Arrays.asList(Card.of(Suit.HEART, Denomination.TEN),
                Card.of(Suit.DIAMOND, Denomination.NINE));
        Dealer dealer = new Dealer(new Blackjack(new Cards(blackjackCards)));
        List<Player> players = Arrays.asList(
                new Player("하나", 1000, new Blackjack(new Cards(blackjackCards))),
                new Player("둘", 2000, new Hit(new Cards(cards))));
        BlackjackGame blackjackGame = new BlackjackGame(dealer, players);

        // when, then
        assertThatThrownBy(blackjackGame::getResult).isInstanceOf(GameNotEndException.class);
    }
}
