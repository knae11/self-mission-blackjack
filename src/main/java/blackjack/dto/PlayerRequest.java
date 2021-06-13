package blackjack.dto;

public class PlayerRequest {

    private String name;
    private Integer bettingMoney;

    public PlayerRequest() {
    }

    public PlayerRequest(String name, Integer bettingMoney) {
        this.name = name;
        this.bettingMoney = bettingMoney;
    }

    public String getName() {
        return name;
    }

    public Integer getBettingMoney() {
        return bettingMoney;
    }
}
