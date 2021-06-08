package participant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

public class DealerTest {
    @DisplayName("딜러 생성")
    @Test
    void createDealer() {
        assertThatCode(Dealer::new).doesNotThrowAnyException();
    }
}
