package exception.card;

public class DeckEmptyException extends CustomException{
    private static final String MESSAGE = "남은 카드가 없어요.";
    public DeckEmptyException() {
        super(MESSAGE);
    }
}
