package blackjack.exception.web;

import blackjack.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ParticipantIdNotFoundException extends CustomException {
    private static final String MESSAGE = "해당 참가자가 없습니다.";

    public ParticipantIdNotFoundException() {
        super(HttpStatus.NOT_FOUND, MESSAGE);
    }
}
