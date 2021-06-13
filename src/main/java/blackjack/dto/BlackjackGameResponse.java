package blackjack.dto;

import java.util.List;

public class BlackjackGameResponse {
    private Long gameId;
    private List<ParticipantResponse> participants;

    public BlackjackGameResponse() {
    }

    public BlackjackGameResponse(Long gameId, List<ParticipantResponse> participants) {
        this.gameId = gameId;
        this.participants = participants;
    }

    public Long getGameId() {
        return gameId;
    }

    public List<ParticipantResponse> getParticipants() {
        return participants;
    }
}
