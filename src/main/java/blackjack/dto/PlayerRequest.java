package blackjack.dto;

import blackjack.exception.validator.NameNotDealer;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class PlayerRequest {
    @NotBlank
    @NameNotDealer
    private String name;

    @Min(1000)
    private Integer bettingMoney;

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
