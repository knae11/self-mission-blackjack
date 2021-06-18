package blackjack.dto;

import java.util.List;

public class ParticipantsResponse {
    private List<ParticipantResponse> participants;

    public ParticipantsResponse() {
    }

    public ParticipantsResponse(List<ParticipantResponse> participants) {
        this.participants = participants;
    }

    public List<ParticipantResponse> getParticipants() {
        return participants;
    }
}
