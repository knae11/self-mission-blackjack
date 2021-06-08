package card;

public enum Denomination {
    ACE("a",1),
    TWO("2",2),
    THREE("3",3),
    FOUR("4",4),
    FIVE("5",5),
    SIX("6",6),
    SEVEN("7",7),
    EIGHT("8",8),
    NINE("9",9),
    TEN("10",10),
    JACK("j",10),
    QUEEN("q",10),
    KING("k",10);

    private final String id;
    private final int score;

    Denomination(String id, int score) {
        this.id = id;
        this.score = score;
    }

    public String getId() {
        return id;
    }
}
