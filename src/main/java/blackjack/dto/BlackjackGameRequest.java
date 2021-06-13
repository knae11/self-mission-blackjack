package blackjack.dto;

import java.util.List;

public class BlackjackGameRequest {
    private List<PlayerRequest> playerRequests;

    public BlackjackGameRequest() {
    }

    public BlackjackGameRequest(List<PlayerRequest> playerRequests) {
        this.playerRequests = playerRequests;
    }

    public List<PlayerRequest> getPlayerRequests() {
        return playerRequests;
    }
}
