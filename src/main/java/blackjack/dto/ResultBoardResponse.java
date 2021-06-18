package blackjack.dto;

import blackjack.domain.result.Result;

import java.util.Map;

public class ResultBoardResponse {
    private Integer win;
    private Integer draw;
    private Integer lose;

    public ResultBoardResponse() {
    }

    private ResultBoardResponse(Integer win, Integer draw, Integer lose) {
        this.win = win;
        this.draw = draw;
        this.lose = lose;
    }

    public static ResultBoardResponse of(Map<Result, Integer> results) {
        return new ResultBoardResponse(results.get(Result.WIN), results.get(Result.DRAW), results.get(Result.LOSE));
    }

    public Integer getWin() {
        return win;
    }

    public Integer getDraw() {
        return draw;
    }

    public Integer getLose() {
        return lose;
    }
}
