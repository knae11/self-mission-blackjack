package blackjack.ui;

import blackjack.application.BlackjackService;
import blackjack.dto.BlackjackGameRequest;
import blackjack.dto.BlackjackGameResponse;
import blackjack.dto.ParticipantsResponse;
import blackjack.dto.PlayerRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/blackjack")
public class BlackjackController {
    private final BlackjackService blackjackService;

    public BlackjackController(BlackjackService blackjackService) {
        this.blackjackService = blackjackService;
    }

    @PostMapping
    public ResponseEntity<BlackjackGameResponse> createRoom(@RequestBody BlackjackGameRequest blackjackGameRequest) {
        List<PlayerRequest> playerRequests = blackjackGameRequest.getPlayerRequests();
        BlackjackGameResponse blackjackGameResponse = blackjackService.createGame(playerRequests);
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/api/blackjack/" + blackjackGameResponse.getGameId()))
                .body(blackjackGameResponse);
    }

    @GetMapping("/{gameId}/participants")
    public ResponseEntity<ParticipantsResponse> findParticipants(@PathVariable Long gameId) {
        ParticipantsResponse participantsResponse = blackjackService.findParticipants(gameId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(participantsResponse);
    }
}
