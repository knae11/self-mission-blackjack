package blackjack.exception.domain.card;

import blackjack.exception.CustomException;
import org.springframework.http.HttpStatus;

public class CardEmptyException extends CustomException {
    private static final String MESSAGE = "카드가 없어요.";

    public CardEmptyException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
    }
}
