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
    @DisplayName("방생성")
    @Test
    void createRoom() {
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .when().post("/api/blackjack")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotNull();
        assertThat(response.as(RoomResponse.class).getRoomId()).isNotNull();
    }
}
