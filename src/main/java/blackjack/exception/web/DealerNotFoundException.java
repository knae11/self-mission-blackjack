package blackjack.exception.web;

import blackjack.exception.CustomException;
import org.springframework.http.HttpStatus;

public class DealerNotFoundException extends CustomException {
    private static final String MESSAGE = "딜러가 없습니다.";

    public DealerNotFoundException() {
        super(HttpStatus.NOT_FOUND, MESSAGE);
    }
}
