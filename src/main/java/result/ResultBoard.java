package result;

import participant.Dealer;
import participant.Participant;
import participant.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResultBoard {
    private final List<Participant> participants;
    private final Dealer dealer;
    private final List<Player> players;

    public ResultBoard(List<Participant> participants) {
        this.participants = participants;
        this.players = extractPlayers(participants);
        this.dealer = extractDealer(participants);
    }

    private Dealer extractDealer(List<Participant> participants) {
        return participants.stream()
                .filter(participant -> !participant.isPlayer())
                .map(participant -> (Dealer) participant)
                .findFirst()
                .get();
    }

    private List<Player> extractPlayers(List<Participant> participants) {
        return participants.stream()
                .filter(Participant::isPlayer)
                .map(participant -> (Player) participant)
                .collect(Collectors.toList());
    }


    public List<ParticipantResult> getResults() {
        final Map<Player, Result> playerResults = getPlayerResults();
        final Map<Participant, Integer> moneyResults = getMoneyResults(playerResults);

        return getParticipantFinalResults(playerResults, moneyResults);
    }

    private List<ParticipantResult> getParticipantFinalResults(Map<Player, Result> playerResults, Map<Participant, Integer> moneyResults) {
        final List<ParticipantResult> finalResults = new ArrayList<>();

        finalResults.add(ParticipantResult.createDealer(
                dealer.getName(), new ArrayList<>(playerResults.values()), moneyResults.get(dealer)));

        for (Player player : players) {
            finalResults.add(
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
