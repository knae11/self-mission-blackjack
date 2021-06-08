package participant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@DisplayName("[도메인] 플레이어")
public class PlayerTest {
    @DisplayName("플레이어 생성")
    @Test
    void createPlayer() {
        assertThatCode(() -> new Player("better", 1000)).doesNotThrowAnyException();
    }


}
