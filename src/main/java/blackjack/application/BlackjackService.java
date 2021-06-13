package blackjack.application;

import blackjack.dao.BlackjackGameDao;
import blackjack.domain.card.Deck;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlackjackService {

    private final BlackjackGameDao blackjackgameDao;

    public BlackjackService(BlackjackGameDao blackjackgameDao) {
        this.blackjackgameDao = blackjackgameDao;
    }

    public Long createRoom() {
        Deck deck = Deck.createShuffled();
        List<String> cardIds = deck.getCardIds();
        String cardDeck = String.join("/", cardIds);
        return blackjackgameDao.createRoom(cardDeck);
    }
}
