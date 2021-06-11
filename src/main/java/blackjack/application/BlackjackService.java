package blackjack.application;

import blackjack.dao.RoomDao;
import blackjack.domain.card.Deck;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlackjackService {

    private final RoomDao roomDao;

    public BlackjackService(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    public Long createRoom() {
        Deck deck = Deck.createShuffled();
        List<String> cardIds = deck.getCardIds();
        String cardDeck = String.join("/", cardIds);
        return roomDao.createRoom(cardDeck);
    }
}
