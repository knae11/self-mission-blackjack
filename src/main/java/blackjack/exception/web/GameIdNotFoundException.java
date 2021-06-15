package blackjack.exception.web;

import blackjack.exception.CustomException;
import org.springframework.http.HttpStatus;

public class GameIdNotFoundException extends CustomException {
    private static final String MESSAGE = "해당 게임이 없습니다.";

    public GameIdNotFoundException() {
        super(HttpStatus.NOT_FOUND, MESSAGE);
    }
}
