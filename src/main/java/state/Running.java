package state;

abstract class Running implements State {
    protected static final int BLACKJACK = 21;

    @Override
    public boolean isBlackjack() {
        return false;
    }

    @Override
    public boolean isRunning() {
        return true;
    }

    @Override
    public boolean isBust() {
        return false;
    }

    @Override
    public boolean isStay() {
        return false;
    }
}
