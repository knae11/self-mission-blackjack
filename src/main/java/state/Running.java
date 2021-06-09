package state;

abstract class Running implements State {


    @Override
    public boolean isBlackjack() {
        return false;
    }

}
