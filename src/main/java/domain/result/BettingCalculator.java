package domain.result;

import domain.participant.Dealer;
import domain.participant.Player;

public class BettingCalculator {
    private final Dealer dealer;
    private int dealerMoney = 0;

    public BettingCalculator(Dealer dealer) {
        this.dealer = dealer;
    }

    public int getResult(Player player, Result result) {
        int initialPlayerMoney = player.getInitialBetting();
        int finalPlayerMoney = (int) (player.getInitialBetting() * result.getBettingRate());
        dealerMoney += (initialPlayerMoney - finalPlayerMoney);
        return finalPlayerMoney;
    }

    public int getDealerMoney(){
        return dealerMoney;
    }

}
