package blackjack.exception.card;

import blackjack.exception.UnreachableCustomException;

public class CardInvalidException extends UnreachableCustomException {
    private static final String MESSAGE = "유효하지 않은 카드 입니다.";

    public CardInvalidException() {
        super(MESSAGE);
    }
}
