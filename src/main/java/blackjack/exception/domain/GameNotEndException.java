package blackjack.exception.domain;

import blackjack.exception.CustomException;
import org.springframework.http.HttpStatus;

public class GameNotEndException extends CustomException {
    private static final String MESSAGE = "게임이 종료되지 않았습니다.";

    public GameNotEndException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
    }
}
