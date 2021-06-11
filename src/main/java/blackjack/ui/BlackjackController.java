package blackjack.ui;

import blackjack.dto.RoomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/blackjack")
public class BlackjackController {
    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(){
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }
}
