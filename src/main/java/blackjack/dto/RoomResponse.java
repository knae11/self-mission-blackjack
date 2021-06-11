package blackjack.dto;

public class RoomResponse {
    private final Long roomId;

    public RoomResponse(Long roomId) {
        this.roomId = roomId;
    }

    public Long getRoomId() {
        return roomId;
    }
}
