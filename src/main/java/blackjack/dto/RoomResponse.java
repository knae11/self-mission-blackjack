package blackjack.dto;

public class RoomResponse {
    private Long roomId;

    public RoomResponse() {
    }

    public RoomResponse(Long roomId) {
        this.roomId = roomId;
    }

    public Long getRoomId() {
        return roomId;
    }
}
