package blackjack.application;

import blackjack.domain.card.Cards;
import blackjack.domain.state.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class StateConvertor {
    private static final String BLACKJACK = "blackjack";
    private static final String BUST = "bust";
    private static final String HIT = "hit";
    private static final String INIT_TURN = "initTurn";
    private static final String STAY = "stay";

    private static final Map<String, Function<Cards, State>> depressCache = new HashMap<>();

    static {
        depressCache.put(BLACKJACK, Blackjack::new);
        depressCache.put(BUST, Bust::new);
        depressCache.put(HIT, Hit::new);
        depressCache.put(INIT_TURN, (cards) -> new InitTurn());
        depressCache.put(STAY, Stay::new);
    }

    public static State depressToState(String stateValue, Cards cards){
        return depressCache.get(stateValue).apply(cards);
    }

    public static String compressToString(State state){
        //todo: ??? 같은 타입인 것을 확인!??!
    }

}
