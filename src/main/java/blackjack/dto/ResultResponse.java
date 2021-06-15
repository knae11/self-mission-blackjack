package blackjack.dto;

import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Participant;
import blackjack.domain.participant.Player;
import blackjack.domain.result.ParticipantResult;
import blackjack.exception.web.DealerNotFoundException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResultResponse {
    private Long id;
    private String name;
    private ResultBoardResponse result;
    private List<CardResponse> cards;
    private Integer money;

    public ResultResponse() {
    }

    private ResultResponse(Long id, String name, ResultBoardResponse result, List<CardResponse> cards, Integer money) {
        this.id = id;
        this.name = name;
        this.result = result;
        this.cards = cards;
        this.money = money;
    }

    private ResultResponse(Participant participant, Map<Participant, ParticipantResult> result) {
        this(participant.getId(),
                participant.getName(),
                getResults(result, participant),
                CardResponse.listOf((participant.getCards())),
                result.get(participant).getMoneyResult());
    }

    public static List<ResultResponse> listOf(Map<Participant, ParticipantResult> result) {
        List<ResultResponse> responses = new ArrayList<>();
        Dealer dealer = getDealer(result);
        List<Player> players = getPlayers(result);

        responses.add(new ResultResponse(dealer, result));
        players.forEach(player -> responses.add(new ResultResponse(player, result)));

        return responses;
    }

    private static ResultBoardResponse getResults(Map<Participant, ParticipantResult> result, Participant participant) {
        return ResultBoardResponse.of(result.get(participant).getResults());
    }

    private static List<Player> getPlayers(Map<Participant, ParticipantResult> result) {
        return result.keySet().stream()
                .filter(Participant::isPlayer)
                .map(x -> (Player) x)
                .sorted(Comparator.comparing(Player::getId))
                .collect(Collectors.toList());
    }

    private static Dealer getDealer(Map<Participant, ParticipantResult> result) {
        return result.keySet().stream()
                .filter(participant -> !participant.isPlayer())
                .map(x -> (Dealer) x)
                .findFirst()
                .orElseThrow(DealerNotFoundException::new);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ResultBoardResponse getResult() {
        return result;
    }

    public List<CardResponse> getCards() {
        return cards;
    }

    public Integer getMoney() {
        return money;
    }
}
