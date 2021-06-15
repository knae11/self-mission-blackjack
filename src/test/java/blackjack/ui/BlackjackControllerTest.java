package blackjack.ui;

import blackjack.application.BlackjackService;
import blackjack.dto.BlackjackGameRequest;
import blackjack.dto.CardTakingRequest;
import blackjack.dto.PlayerRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@DisplayName("[Controller] Request 예외테스트")
class BlackjackControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BlackjackService blackjackService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }


    @DisplayName("PlayerRequest name is null")
    @Test
    void createRoomPlayerNameNull() throws Exception {
        BlackjackGameRequest request = new BlackjackGameRequest(Arrays.asList(new PlayerRequest(null, 1000)));
        String requestValue = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/blackjack")
                .content(requestValue)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("PlayerRequest name is Blank")
    @Test
    void createRoomPlayerNameBlank() throws Exception {
        BlackjackGameRequest request = new BlackjackGameRequest(Arrays.asList(new PlayerRequest("", 1000)));
        String requestValue = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/blackjack")
                .content(requestValue)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("PlayerRequest bettingMoney less than 1000")
    @Test
    void createRoomPlayerBettingMoneyLessThan1000() throws Exception {
        BlackjackGameRequest request = new BlackjackGameRequest(Arrays.asList(new PlayerRequest("아무거나", 990)));
        String requestValue = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/blackjack")
                .content(requestValue)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isBadRequest())
                .andDo(print());

    }

    @DisplayName("PlayerRequest name 딜러")
    @Test
    void createRoomPlayerNameDealer() throws Exception {
        BlackjackGameRequest request = new BlackjackGameRequest(Arrays.asList(new PlayerRequest("딜러", 1000)));
        String requestValue = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/blackjack")
                .content(requestValue)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isBadRequest())
                .andDo(print());

    }

    @DisplayName("roomId is null")
    @Test
    void findParticipants() throws Exception {
        Long gameId = null;

        mockMvc.perform(get("/api/blackjack/" + gameId))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("participantId is null")
    @Test
    void findPlayer() throws Exception {
        Long participantId = null;

        mockMvc.perform(get("/api/blackjack/1/players/" + participantId))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("플레이어 카드 받기 request - Boolean is null")
    @Test
    void takeDealerCard() throws Exception {
        CardTakingRequest request = new CardTakingRequest(null);
        String requestValue = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/blackjack/1/players/1")
                .content(requestValue)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

}