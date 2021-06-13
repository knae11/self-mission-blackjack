package blackjack.dto;

import java.util.List;

public class BlackjackGameResponse {
    private Long gameId;
    private DealerResponse dealer;
    private List<ParticipantResponse> participants;

    public BlackjackGameResponse() {
    }


    public BlackjackGameResponse(Long gameId, DealerResponse dealer, List<ParticipantResponse> participants) {
        this.gameId = gameId;
        this.dealer = dealer;
        this.participants = participants;
    }

    public Long getGameId() {
        return gameId;
    }

    public DealerResponse getDealer() {
        return dealer;
    }

    public List<ParticipantResponse> getParticipants() {
        return participants;
    }
}
