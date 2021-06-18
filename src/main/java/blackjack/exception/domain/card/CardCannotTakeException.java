package blackjack.exception.domain.card;

import blackjack.exception.CustomException;
import org.springframework.http.HttpStatus;

public class CardCannotTakeException extends CustomException {
    private static final String MESSAGE = "카드를 받을 수 없습니다.";

    public CardCannotTakeException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
    }
}
