package blackjack.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class BlackjackGameRequest {
    @NotNull
    private List<@Valid PlayerRequest> playerRequests;

    public BlackjackGameRequest() {
    }

    public BlackjackGameRequest(List<PlayerRequest> playerRequests) {
        this.playerRequests = playerRequests;
    }

    public List<PlayerRequest> getPlayerRequests() {
        return playerRequests;
    }
}
