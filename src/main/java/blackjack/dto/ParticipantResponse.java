package blackjack.dto;

import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Participant;
import blackjack.domain.participant.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParticipantResponse {
    private Long participantId;
    private String name;
    private Integer bettingMoney;
    private List<CardResponse> cards;
    private String state;

    public ParticipantResponse(Long participantId, String name, Integer bettingMoney, List<CardResponse> cards, String state) {
        this.participantId = participantId;
        this.name = name;
        this.bettingMoney = bettingMoney;
        this.cards = cards;
        this.state = state;
    }

    public static List<ParticipantResponse> listOf(Dealer dealer, List<Player> players) {
        List<Participant> participants = new ArrayList<>();
        participants.add(dealer);
        participants.addAll(players);

        return participants.stream()
                .map(participant -> new ParticipantResponse(participant.getId(), participant.getName(), participant.getInitialBetting(), CardResponse.listOf(participant.getCards()), participant.getStateToString()))
                .collect(Collectors.toList());
    }

    public Long getParticipantId() {
        return participantId;
    }

    public String getName() {
        return name;
    }

    public Integer getBettingMoney() {
        return bettingMoney;
    }

    public List<CardResponse> getCards() {
        return cards;
    }

    public String getState() {
        return state;
    }
}
