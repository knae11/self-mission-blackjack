package blackjack.ui;

import blackjack.AcceptanceTest;
import blackjack.dto.*;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[인수] API")
public class BlackjackAcceptanceTest extends AcceptanceTest {
    @DisplayName("게임 생성")
    @Test
    void createGame() {
        List<PlayerRequest> playerRequests = Arrays.asList(
                new PlayerRequest("안녕", 1000),
                new PlayerRequest("바이", 3000));
        BlackjackGameRequest request = new BlackjackGameRequest(playerRequests);

        ExtractableResponse<Response> response = 게임생성(request);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotNull();
        BlackjackGameResponse blackjackGame = response.as(BlackjackGameResponse.class);
        assertThat(blackjackGame.getGameId()).isNotNull();
        assertThat(blackjackGame.getDealer()).isNotNull();
        assertThat(blackjackGame.getParticipants()).hasSize(2);
    }

    @DisplayName("게임 전체 참가자 조회")
    @Test
    void findParticipants() {
        List<PlayerRequest> playerRequests = Arrays.asList(
                new PlayerRequest("안녕", 1000),
                new PlayerRequest("바이", 3000));
        BlackjackGameRequest request = new BlackjackGameRequest(playerRequests);
        Long gameId = 아이디조회(게임생성(request));

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .when().get("/api/blackjack/" + gameId)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        BlackjackGameResponse blackjackGameResponse = response.as(BlackjackGameResponse.class);
        assertThat(blackjackGameResponse.getParticipants()).hasSize(2);
    }

    @DisplayName("게임 플레이어 전체 조회")
    @Test
    void findPlayers() {
        List<PlayerRequest> playerRequests = Arrays.asList(
                new PlayerRequest("안녕", 1000),
                new PlayerRequest("바이", 3000));
        BlackjackGameRequest request = new BlackjackGameRequest(playerRequests);
        Long gameId = 아이디조회(게임생성(request));

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .when().get("/api/blackjack/" + gameId + "/players")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        ParticipantsResponse participantsResponse = response.as(ParticipantsResponse.class);
        assertThat(participantsResponse.getParticipants()).hasSize(2);
    }

    @DisplayName("게임 딜러 조회")
    @Test
    void findDealer() {
        List<PlayerRequest> playerRequests = Arrays.asList(
                new PlayerRequest("안녕", 1000),
                new PlayerRequest("바이", 3000));
        BlackjackGameRequest request = new BlackjackGameRequest(playerRequests);
        Long gameId = 아이디조회(게임생성(request));

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .when().get("/api/blackjack/" + gameId + "/dealer")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        DealerResponse dealerResponse = response.as(DealerResponse.class);
        assertThat(dealerResponse.getParticipantId()).isNotNull();
        assertThat(dealerResponse.getCards()).hasSize(1);
    }

    @DisplayName("개별 플레이어 조회")
    @Test
    void findPlayer() {
        List<PlayerRequest> playerRequests = Arrays.asList(
                new PlayerRequest("안녕", 1000),
                new PlayerRequest("바이", 3000));
        BlackjackGameRequest request = new BlackjackGameRequest(playerRequests);
        ExtractableResponse<Response> gameResponse = 게임생성(request);
        Long gameId = 아이디조회(gameResponse);
        Long playerId = gameResponse.as(BlackjackGameResponse.class).getParticipants().get(0).getParticipantId();

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .when().get("/api/blackjack/" + gameId + "/players/" + playerId)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        ParticipantResponse participantResponse = response.as(ParticipantResponse.class);
        assertThat(participantResponse.getParticipantId()).isNotNull();
        assertThat(participantResponse.getCards()).hasSize(2);
    }

    @DisplayName("개별 플레이어 카드 받을 수 있는지 여부 조회")
    @Test
    void isPlayerAbleToTakeCard() {
        List<PlayerRequest> playerRequests = Arrays.asList(
                new PlayerRequest("안녕", 1000),
                new PlayerRequest("바이", 3000));
        BlackjackGameRequest request = new BlackjackGameRequest(playerRequests);
        ExtractableResponse<Response> gameResponse = 게임생성(request);
        Long gameId = 아이디조회(gameResponse);
        Long playerId = gameResponse.as(BlackjackGameResponse.class).getParticipants().get(0).getParticipantId();

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .when().get("/api/blackjack/" + gameId + "/players/" + playerId + "/availability")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        AvailabilityResponse availabilityResponse = response.as(AvailabilityResponse.class);
        assertThat(availabilityResponse.getIsAbleToTake()).isNotNull();
    }

    private Long 아이디조회(ExtractableResponse<Response> response) {
        return response.as(BlackjackGameResponse.class).getGameId();
    }

    private ExtractableResponse<Response> 게임생성(BlackjackGameRequest request) {
        return RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/blackjack")
                .then().log().all()
                .extract();
    }
}
