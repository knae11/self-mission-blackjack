package blackjack.dto;

import blackjack.domain.participant.Dealer;

import java.util.List;

public class DealerResponse {
    private Long participantId;
    private String name;
    private List<CardResponse> cards;
    private String state;

    public DealerResponse() {
    }

    private DealerResponse(Long participantId, String name, List<CardResponse> cards, String state) {
        this.participantId = participantId;
        this.name = name;
        this.cards = cards;
        this.state = state;
    }

    public static DealerResponse of(Dealer dealer) {
        return new DealerResponse(dealer.getId(), dealer.getName(), CardResponse.dealerListOf(dealer.getCards()), dealer.getStateToString());
    }

    public Long getParticipantId() {
        return participantId;
    }

    public String getName() {
        return name;
    }

    public List<CardResponse> getCards() {
        return cards;
    }

    public String getState() {
        return state;
    }
}
