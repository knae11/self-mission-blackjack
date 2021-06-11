package domain.card;

public enum Suit {
    HEART("h"),
    CLOVER("c"),
    DIAMOND("d"),
    SPADE("s");

    private final String id;

    Suit(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
