package blackjack.domain;

import blackjack.domain.card.Deck;
import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

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
}
