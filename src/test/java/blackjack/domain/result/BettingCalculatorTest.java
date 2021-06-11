package blackjack.domain.result;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Player;

import static org.assertj.core.api.Assertions.assertThat;
import static blackjack.domain.result.Result.*;

@DisplayName("[도메인] 베팅금액계산기")
public class BettingCalculatorTest {
    @DisplayName("이겼을 경우, 베팅금액의 1.5배를 한다.")
    @Test
    void win() {
        Dealer dealer = new Dealer();
        Player player = new Player("better", 1000);
        BettingCalculator bettingCalculator = new BettingCalculator(dealer);

        assertThat(bettingCalculator.getResult(player, WIN)).isEqualTo(1500);
        assertThat(bettingCalculator.getDealerMoney()).isEqualTo(-500);
    }

    @DisplayName("졌을 경우, 베팅금액의 0.0배를 한다.")
    @Test
    void lose() {
        Dealer dealer = new Dealer();
        Player player = new Player("better", 1000);
        BettingCalculator bettingCalculator = new BettingCalculator(dealer);

        assertThat(bettingCalculator.getResult(player, LOSE)).isEqualTo(0);
        assertThat(bettingCalculator.getDealerMoney()).isEqualTo(1000);
    }

    @DisplayName("비겼을 경우, 베팅금액의 1.0배를 한다.")
    @Test
    void draw() {
        Dealer dealer = new Dealer();
        Player player = new Player("better", 1000);
        BettingCalculator bettingCalculator = new BettingCalculator(dealer);

        assertThat(bettingCalculator.getResult(player, DRAW)).isEqualTo(1000);
        assertThat(bettingCalculator.getDealerMoney()).isEqualTo(0);
    }
}
