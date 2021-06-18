package blackjack.domain.result;

import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Participant;
import blackjack.domain.participant.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultBoard {
    private final Dealer dealer;
    private final List<Player> players;


    public ResultBoard(Dealer dealer, List<Player> players) {
        this.dealer = dealer;
        this.players = players;
    }

    public Map<Participant, ParticipantResult> getResults() {
        final Map<Player, Result> playerResults = getPlayerResults();
        final Map<Participant, Integer> moneyResults = getMoneyResults(playerResults);

        return getParticipantFinalResults(playerResults, moneyResults);
    }

    private Map<Participant, ParticipantResult> getParticipantFinalResults(Map<Player, Result> playerResults, Map<Participant, Integer> moneyResults) {
        final Map<Participant, ParticipantResult> finalResults = new HashMap<>();

        finalResults.put(dealer, ParticipantResult.createDealer(
                dealer.getName(), new ArrayList<>(playerResults.values()), moneyResults.get(dealer)));

        for (Player player : players) {
            finalResults.put(player,
                    ParticipantResult.createPlayer(player.getName(), playerResults.get(player), moneyResults.get(player)));
        }

        return finalResults;
    }

    private Map<Player, Result> getPlayerResults() {
        final Map<Player, Result> playerResults = new HashMap<>();
        Judge judge = new Judge(dealer);
        players.forEach(player -> {
            Result result = judge.getResultOfPlayer(player);
            playerResults.put(player, result);
        });
        return playerResults;
    }

    private Map<Participant, Integer> getMoneyResults(Map<Player, Result> playerResults) {
        final Map<Participant, Integer> results = new HashMap<>();
        BettingCalculator bettingCalculator = new BettingCalculator(dealer);
        players.forEach(player -> {
            int finalMoney = bettingCalculator.getResult(player, playerResults.get(player));
            results.put(player, finalMoney);
        });

        results.put(dealer, bettingCalculator.getDealerMoney());
        return results;
    }
}
