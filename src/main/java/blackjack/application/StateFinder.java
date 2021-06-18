package blackjack.application;

import blackjack.domain.card.Cards;
import blackjack.domain.state.*;
import blackjack.exception.web.StateNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class StateFinder {
    private static final Map<String, Function<Cards, State>> depressCache;
    private static final Map<Predicate<State>, String> compressCache;
    private static final String BLACKJACK = "blackjack";
    private static final String STAY = "stay";
    private static final String BUST = "bust";
    private static final String HIT = "hit";

    static {
        depressCache = new HashMap<>();

        depressCache.put(BLACKJACK, Blackjack::new);
        depressCache.put(STAY, Stay::new);
        depressCache.put(BUST, Bust::new);
        depressCache.put(HIT, Hit::new);

        compressCache = new HashMap<>();

        compressCache.put(State::isBlackjack, BLACKJACK);
        compressCache.put(State::isStay, STAY);
        compressCache.put(State::isBust, BUST);
        compressCache.put(State::isRunning, HIT);
    }

    public static State findState(String name, Cards cards) {
        return depressCache.get(name).apply(cards);
    }

    public static String convertToString(State stateValue) {
        Predicate<State> foundState = compressCache.keySet().stream()
                .filter(state -> state.test(stateValue))
                .findFirst()
                .orElseThrow(StateNotFoundException::new);
        return compressCache.get(foundState);
    }
}
