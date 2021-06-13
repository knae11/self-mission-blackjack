package blackjack.dto;

public class ParticipantResponse {
    private Long id;
    private String name;
    private Integer bettingMoney;

    public ParticipantResponse(Long id, String name, Integer bettingMoney) {
        this.id = id;
        this.name = name;
        this.bettingMoney = bettingMoney;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getBettingMoney() {
        return bettingMoney;
    }
}
