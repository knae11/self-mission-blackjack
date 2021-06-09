package exception.card;

import exception.CustomException;

public class EmptyException extends CustomException {
    private static final String MESSAGE = "카드가 없어요.";

    public EmptyException() {
        super(MESSAGE);
    }
}
