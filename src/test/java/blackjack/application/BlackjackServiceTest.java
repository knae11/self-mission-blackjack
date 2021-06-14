package blackjack.application;

import blackjack.dao.BlackjackGameDao;
import blackjack.dao.ParticipantDao;
import blackjack.domain.card.Card;
import blackjack.domain.card.Cards;
import blackjack.domain.card.Denomination;
import blackjack.domain.card.Suit;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("[Service]")
@ExtendWith(MockitoExtension.class)
class BlackjackServiceTest {

    @Mock
    private BlackjackGameDao blackjackGameDao;
    @Mock
    private ParticipantDao participantDao;

    @InjectMocks
    private BlackjackService blackjackService;

    @DisplayName("결과 테스트")
    @Test
    void getResult() {
        when(blackjackGameDao.findDealerId(1L)).thenReturn(1L);
        when(blackjackGameDao.findPlayerIds(1L)).thenReturn("2,3");
        List<Card> card20 = Arrays.asList(Card.of(Suit.DIAMOND, Denomination.TEN),
                Card.of(Suit.SPADE, Denomination.TEN));
        List<Card> card19 = Arrays.asList(Card.of(Suit.DIAMOND, Denomination.TEN),
                Card.of(Suit.SPADE, Denomination.NINE));
        when(participantDao.findDealerById(1L)).thenReturn(new Dealer(1L, new Stay(new Cards(card20))));
        when(participantDao.findPlayerById(2L)).thenReturn(new Player(2L, "111", 1000, new Stay(new Cards(card20))));
        when(participantDao.findPlayerById(3L)).thenReturn(new Player(3L, "222", 2000, new Stay(new Cards(card19))));

        List<ResultResponse> result = blackjackService.getResult(1L);

        assertThat(result).hasSize(3);
        assertThat(result.get(0).getName()).isEqualTo("딜러");
        assertThat(result.get(0).getMoney()).isEqualTo(2000);

    }
}