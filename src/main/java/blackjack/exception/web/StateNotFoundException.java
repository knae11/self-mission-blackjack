package blackjack.exception.web;

import blackjack.exception.CustomException;
import org.springframework.http.HttpStatus;

public class StateNotFoundException extends CustomException {
    private static final String MESSAGE = "해당하는 상태가 없습니다.";

    public StateNotFoundException() {
        super(HttpStatus.NOT_FOUND, MESSAGE);
    }
}
