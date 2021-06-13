package blackjack.dto;

public class CardTakingRequest {
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
