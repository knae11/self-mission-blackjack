package result;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static result.Result.*;

public class ParticipantResult {
    private final String name;
    private final HashMap<Result, Integer> results;
    private final int moneyResult;

    private ParticipantResult(String name, List<Result> resultValues, int moneyResult) {
        this.name = name;
        this.results = new HashMap<>();
        setResults(resultValues);
        this.moneyResult = moneyResult;
    }

    public static ParticipantResult createPlayer(String name, Result resultValue, int moneyResult) {
        return new ParticipantResult(name, Arrays.asList(resultValue), moneyResult);
    }

    public static ParticipantResult createDealer(String name, List<Result> resultValues, int moneyResult){
        return new ParticipantResult(name, reverse(resultValues), moneyResult);
    }

    private static List<Result> reverse(List<Result> resultValues) {
        return resultValues.stream()
                .map(Result::reverse)
                .collect(Collectors.toList());
    }

    private void setResults(List<Result> resultValues) {
        this.results.put(WIN, countResult(resultValues, WIN));
        this.results.put(DRAW, countResult(resultValues, DRAW));
        this.results.put(LOSE, countResult(resultValues, LOSE));
    }

    private int countResult(List<Result> resultValues, Result resultState) {
        return (int) resultValues.stream()
                .filter(result -> result == resultState)
                .count();
    }

    public String getName() {
        return name;
    }

    public HashMap<Result, Integer> getResults() {
        return results;
    }

    public int getMoneyResult() {
        return moneyResult;
    }
}
