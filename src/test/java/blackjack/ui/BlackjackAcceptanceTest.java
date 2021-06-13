package blackjack.ui;

import blackjack.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;

@DisplayName("[인수] API")
public class BlackjackAcceptanceTest extends AcceptanceTest {
//    @DisplayName("방 생성")
//    @Test
//    void createRoom() {
//        ExtractableResponse<Response> response = RestAssured
//                .given().log().all()
//                .when().post("/api/blackjack")
//                .then().log().all()
//                .extract();
//
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
//        assertThat(response.header("Location")).isNotNull();
//        assertThat(response.as(RoomResponse.class).getRoomId()).isNotNull();
//    }
}
