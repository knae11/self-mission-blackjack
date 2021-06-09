package exception.card;

public class GameIsDoneException extends CustomException {
    private static final String MESSAGE = "게임이 끝났습니다.";
    public GameIsDoneException() {
        super(MESSAGE);
    }
}
