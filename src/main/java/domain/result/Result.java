package domain.result;

public enum Result {
    WIN(1.5),
    DRAW(1.0),
    LOSE(0.0);

    private final double bettingRate;

    Result(double bettingRate) {
        this.bettingRate = bettingRate;
    }

    public double getBettingRate() {
        return bettingRate;
    }

    public Result reverse() {
        if (this == WIN) {
            return LOSE;
        }
        if (this == LOSE) {
            return WIN;
        }
        return DRAW;
    }
}
