package state;

abstract class Running implements State {
    protected static final int BLACKJACK = 21;

    @Override
    public boolean isBlackjack() {
        return false;
    }

}
