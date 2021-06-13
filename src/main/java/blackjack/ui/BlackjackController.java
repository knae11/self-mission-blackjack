package blackjack.ui;

import blackjack.application.BlackjackService;
import blackjack.dto.*;
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

    @GetMapping("/{gameId}")
    public ResponseEntity<BlackjackGameResponse> findParticipants(@PathVariable Long gameId) {
        BlackjackGameResponse blackjackGameResponse = blackjackService.findParticipants(gameId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(blackjackGameResponse);
    }

    @GetMapping("/{gameId}/players")
    public ResponseEntity<ParticipantsResponse> findPlayers(@PathVariable Long gameId) {
        ParticipantsResponse participantsResponse = blackjackService.findPlayers(gameId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(participantsResponse);
    }

    @GetMapping("/{gameId}/dealer")
    public ResponseEntity<DealerResponse> findDealer(@PathVariable Long gameId) {
        DealerResponse dealerResponse = blackjackService.findDealer(gameId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(dealerResponse);
    }

    @GetMapping("/{gameId}/players/{playerId}")
    public ResponseEntity<ParticipantResponse> findDealer(@PathVariable Long gameId, @PathVariable Long playerId) {
        ParticipantResponse participantResponse = blackjackService.findPlayer(playerId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(participantResponse);
    }
}
