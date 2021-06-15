package blackjack.ui;

import blackjack.application.BlackjackService;
import blackjack.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    public ResponseEntity<BlackjackGameResponse> createRoom(@RequestBody @Valid BlackjackGameRequest blackjackGameRequest) {
        List<PlayerRequest> playerRequests = blackjackGameRequest.getPlayerRequests();
        BlackjackGameResponse blackjackGameResponse = blackjackService.createGame(playerRequests);
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/api/blackjack/" + blackjackGameResponse.getGameId()))
                .body(blackjackGameResponse);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<BlackjackGameResponse> findParticipants(@PathVariable @NotNull Long gameId) {
        BlackjackGameResponse blackjackGameResponse = blackjackService.findParticipants(gameId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(blackjackGameResponse);
    }

    @GetMapping("/{gameId}/players")
    public ResponseEntity<ParticipantsResponse> findPlayers(@PathVariable @NotNull Long gameId) {
        ParticipantsResponse participantsResponse = blackjackService.findPlayers(gameId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(participantsResponse);
    }

    @GetMapping("/{gameId}/dealer")
    public ResponseEntity<DealerResponse> findDealer(@PathVariable @NotNull Long gameId) {
        DealerResponse dealerResponse = blackjackService.findDealer(gameId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(dealerResponse);
    }

    @GetMapping("/{gameId}/players/{playerId}")
    public ResponseEntity<ParticipantResponse> findPlayer(@PathVariable @NotNull Long gameId, @PathVariable @NotNull Long playerId) {
        ParticipantResponse participantResponse = blackjackService.findPlayer(playerId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(participantResponse);
    }

    @GetMapping("/{gameId}/players/{playerId}/availability")
    public ResponseEntity<AvailabilityResponse> findPlayerAbleToTake(@PathVariable @NotNull Long gameId, @PathVariable @NotNull Long playerId) {
        AvailabilityResponse availabilityResponse = blackjackService.findPlayerAbleToTake(playerId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(availabilityResponse);
    }

    @PostMapping("/{gameId}/players/{playerId}")
    public ResponseEntity<Void> takePlayerCard(@PathVariable @NotNull Long gameId, @PathVariable @NotNull Long playerId, @RequestBody @Valid CardTakingRequest cardTakingRequest) {
        blackjackService.takePlayerCard(gameId, playerId, cardTakingRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{gameId}/dealer/{dealerId}/availability")
    public ResponseEntity<AvailabilityResponse> findDealerAbleToTake(@PathVariable @NotNull Long gameId, @PathVariable @NotNull Long dealerId) {
        AvailabilityResponse availabilityResponse = blackjackService.findDealerAbleToTake(dealerId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(availabilityResponse);
    }

    @PostMapping("/{gameId}/dealer/{dealerId}")
    public ResponseEntity<Void> takeDealerCard(@PathVariable @NotNull Long gameId, @PathVariable @NotNull Long dealerId) {
        blackjackService.takeDealerCard(gameId, dealerId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{gameId}/result")
    public ResponseEntity<List<ResultResponse>> getResult(@PathVariable @NotNull Long gameId) {
        List<ResultResponse> resultResponses = blackjackService.getResult(gameId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(resultResponses);
    }
}
