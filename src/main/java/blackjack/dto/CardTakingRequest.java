package blackjack.dto;

import javax.validation.constraints.NotNull;

public class CardTakingRequest {
    @NotNull
    private Boolean isTaking;

    public CardTakingRequest() {
    }

    public CardTakingRequest(Boolean isTaking) {
        this.isTaking = isTaking;
    }

    public Boolean getIsTaking() {
        return isTaking;
    }
}
