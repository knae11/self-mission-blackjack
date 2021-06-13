package blackjack.ui;

import blackjack.AcceptanceTest;
import blackjack.dto.RoomResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[인수] API")
public class BlackjackAcceptanceTest extends AcceptanceTest {
    @DisplayName("방 생성")
    @Test
    void createRoom() {
        ExtractableResponse<Response> response = 방_생성();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotNull();
        assertThat(response.as(RoomResponse.class).getRoomId()).isNotNull();
    }

    private ExtractableResponse<Response> 방_생성() {
        return RestAssured
                .given().log().all()
                .when().post("/api/blackjack")
                .then().log().all()
                .extract();
    }

    private String 방_생성하고_아이디반환() {
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .when().post("/api/blackjack")
                .then().log().all()
                .extract();

        String[] locations = response.header("Location").split("/");
        return locations[locations.length-1];
    }

    @DisplayName("게임시작")
    @Test
    void startGame() {
        String roomId = 방_생성하고_아이디반환();

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .when().post("/api/blackjack/" + roomId + "/start")
                .then().log().all()
                .extract();

        System.out.println( response.jsonPath().getList("."));
    }
}
