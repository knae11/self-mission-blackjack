package exception.card;

import exception.CustomException;

public class CardCannotTakeException extends CustomException {
    private static final String MESSAGE = "카드를 받을 수 없습니다.";

    public CardCannotTakeException() {
        super(MESSAGE);
    }
}
