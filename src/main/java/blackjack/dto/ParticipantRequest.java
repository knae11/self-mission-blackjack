package blackjack.dto;

public class ParticipantRequest {
    private String name;
    private Integer bettingMoney;

    public ParticipantRequest() {
    }

    public ParticipantRequest(String name, Integer bettingMoney) {
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
