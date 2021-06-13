package blackjack.dao;

import blackjack.domain.participant.Participant;
import org.springframework.stereotype.Repository;

@Repository
public class DealerDao {
    public Long create(Participant dealer) {
        return 1L;
    }
}
