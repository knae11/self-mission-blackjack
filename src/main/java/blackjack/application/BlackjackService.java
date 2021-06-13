package blackjack.application;

import blackjack.dao.DealerDao;
import blackjack.dao.PlayerDao;
import blackjack.dao.RoomDao;
import blackjack.domain.card.Card;
import blackjack.domain.card.Deck;
import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Participant;
import blackjack.domain.participant.Player;
import blackjack.dto.ParticipantRequest;
import blackjack.dto.ParticipantResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlackjackService {
    private static final String DELIMITER = "/";

    private final RoomDao roomDao;
    private final DealerDao dealerDao;
    private final PlayerDao playerDao;

    public BlackjackService(RoomDao roomDao, DealerDao dealerDao, PlayerDao playerDao) {
        this.roomDao = roomDao;
        this.dealerDao = dealerDao;
        this.playerDao = playerDao;
    }

    public Long createRoom() {
        Deck deck = Deck.createShuffled();
        List<String> cardIds = deck.getCardIds();
        String cardDeck = String.join(DELIMITER, cardIds);
        return roomDao.createRoom(cardDeck);
    }

    @Transactional
    public List<ParticipantResponse> createParticipants(Long roomId, List<ParticipantRequest> participantRequests) {
        List<Participant> participants = new ArrayList<>();
        Participant dealer = new Dealer();
        List<Player> players = participantRequests.stream()
                .map(participant -> new Player(participant.getName(), participant.getBettingMoney()))
                .collect(Collectors.toList());

        participants.add(dealer);
        participants.addAll(players);

        String deckValues = roomDao.findDeckValues(roomId);
        List<Card> cards = CardConvertor.depressCards(deckValues);
        Deck deck = Deck.listOf(cards);

        participants
                .forEach(participant -> participant.takeCards(deck.drawTwoCards()));

        dealerDao.create(dealer);
        List<Player> createdPlayers = playerDao.create(players);

        return createdPlayers.stream()
                .map(player -> new ParticipantResponse(player.getId(), player.getName(), player.getInitialBetting()))
                .collect(Collectors.toList());
    }
}
