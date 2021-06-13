package blackjack.dto;

public class AvailabilityResponse {
    private Boolean isAbleToTake;

    public AvailabilityResponse() {
    }

    public AvailabilityResponse(Boolean isAbleToTake) {
        this.isAbleToTake = isAbleToTake;
    }

    public boolean getIsAbleToTake() {
        return isAbleToTake;
    }
}
