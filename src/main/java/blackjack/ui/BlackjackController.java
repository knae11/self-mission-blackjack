package blackjack.ui;

import blackjack.application.BlackjackService;
import blackjack.dto.ParticipantRequest;
import blackjack.dto.RoomResponse;
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
    public ResponseEntity<RoomResponse> createRoom() {
        Long roomId = blackjackService.createRoom();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/api/blackjack/" + roomId))
                .body(new RoomResponse(roomId));
    }

    @PostMapping("/{roomId}/start")
    public ResponseEntity<RoomResponse> createParticipants(@PathVariable Long roomId, @RequestBody List<ParticipantRequest> participantRequests) {
        blackjackService.createParticipants(roomId,participantRequests);
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/api/blackjack/" + roomId))
                .body(new RoomResponse(roomId));
    }
}
