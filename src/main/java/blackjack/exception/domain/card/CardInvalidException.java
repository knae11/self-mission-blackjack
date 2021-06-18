package blackjack.exception.domain.card;

import blackjack.exception.CustomException;
import org.springframework.http.HttpStatus;

public class CardInvalidException extends CustomException {
    private static final String MESSAGE = "유효하지 않은 카드 입니다.";

    public CardInvalidException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
    }
}
