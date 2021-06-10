package result;

import participant.Dealer;
import participant.Player;

import static result.Result.*;

public class Judge {
    private final Dealer dealer;
    public Judge(Dealer dealer) {
        this.dealer = dealer;
    }

    public Result getResultOfPlayer(Player player) {
        if(player.isBust()){
            return LOSE;
        }
        if(dealer.isBust()){
            return DRAW;
        }
        return compareScore(player);
    }

    private Result compareScore(Player player) {
        if(dealer.calculateScore() > player.calculateScore()){
            return LOSE;
        }
        if(dealer.calculateScore() < player.calculateScore()){
            return WIN;
        }
        return DRAW;
    }


}
