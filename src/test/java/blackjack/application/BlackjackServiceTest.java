package blackjack.application;

import blackjack.dao.BlackjackGameRepository;
import blackjack.domain.BlackjackGame;
import blackjack.domain.card.*;
import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Player;
import blackjack.domain.state.Stay;
import blackjack.dto.ResultResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("[Service]")
@ExtendWith(MockitoExtension.class)
class BlackjackServiceTest {

    @Mock
    private BlackjackGameRepository blackjackGameRepository;

    @InjectMocks
    private BlackjackService blackjackService;

    @DisplayName("결과 테스트")
    @Test
    void getResult() {
        List<Card> card20 = Arrays.asList(Card.of(Suit.DIAMOND, Denomination.TEN),
                Card.of(Suit.SPADE, Denomination.TEN));
        List<Card> card19 = Arrays.asList(Card.of(Suit.DIAMOND, Denomination.TEN),
                Card.of(Suit.SPADE, Denomination.NINE));
        Dealer dealer = new Dealer(1L, new Stay(new Cards(card20)));
        List<Player> players = Arrays.asList(
                new Player(2L, "111", 1000, new Stay(new Cards(card20))),
                new Player(3L, "222", 2000, new Stay(new Cards(card19)))
        );
        Deck deck = Deck.listOf(Collections.emptyList());
        when(blackjackGameRepository.findByGameId(1L)).thenReturn(BlackjackGame.create(1L, dealer, players, deck));

        List<ResultResponse> result = blackjackService.getResult(1L);

        assertThat(result).hasSize(3);
        assertThat(result.get(0).getName()).isEqualTo("딜러");
        assertThat(result.get(0).getMoney()).isEqualTo(2000);
    }
}