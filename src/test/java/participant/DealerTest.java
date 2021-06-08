package participant;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

public class DealerTest {
    @Test
    void createDealer() {
        assertThatCode(Dealer::new).doesNotThrowAnyException();
    }
}
