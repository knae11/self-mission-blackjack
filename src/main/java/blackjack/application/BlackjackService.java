package blackjack.application;

import blackjack.dao.BlackjackGameDao;
import blackjack.dao.DeckDao;
import blackjack.dao.ParticipantDao;
import blackjack.dao.StateDao;
import blackjack.domain.BlackjackGame;
import blackjack.domain.card.Deck;
import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Player;
import blackjack.domain.state.State;
import blackjack.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlackjackService {

    private final BlackjackGameDao blackjackgameDao;
    private final DeckDao deckDao;
    private final ParticipantDao participantDao;
    private final StateDao stateDao;

    public BlackjackService(BlackjackGameDao blackjackgameDao, DeckDao deckDao, ParticipantDao participantDao, StateDao stateDao) {
        this.blackjackgameDao = blackjackgameDao;
        this.deckDao = deckDao;
        this.participantDao = participantDao;
        this.stateDao = stateDao;
    }

    @Transactional
    public BlackjackGameResponse createGame(List<PlayerRequest> playerRequests) {
        Deck deck = Deck.createShuffled();
        List<Player> players = playerRequests.stream()
                .map(request -> new Player(request.getName(), request.getBettingMoney()))
                .collect(Collectors.toList());
        Dealer dealer = new Dealer();
        BlackjackGame blackjackGame = new BlackjackGame(dealer, players, deck);
        blackjackGame.initGame();

        Dealer createdDealer = participantDao.createDealer(dealer);
        List<Player> createdPlayers = players.stream()
                .map(participantDao::createPlayer)
                .collect(Collectors.toList());
        Deck createdDeck = deckDao.create(deck);

        String playerIds = ListConvertor.compressPlayerIds(createdPlayers);

        Long gameId = blackjackgameDao.create(createdDealer.getId(), playerIds, createdDeck.getId());


        return new BlackjackGameResponse(gameId, DealerResponse.of(createdDealer), ParticipantResponse.listOf(createdPlayers));
    }

    public BlackjackGameResponse findParticipants(Long gameId) {
        Long dealerId = blackjackgameDao.findDealerId(gameId);
        List<Long> playerIds = ListConvertor.depressPlayerIds(blackjackgameDao.findPlayerIds(gameId));

        Dealer dealer = participantDao.findDealerById(dealerId);
        List<Player> players = playerIds.stream()
                .map(participantDao::findPlayerById)
                .collect(Collectors.toList());

        return new BlackjackGameResponse(gameId, DealerResponse.of(dealer), ParticipantResponse.listOf(players));
    }

    public ParticipantsResponse findPlayers(Long gameId) {
        List<Long> playerIds = ListConvertor.depressPlayerIds(blackjackgameDao.findPlayerIds(gameId));

        List<Player> players = playerIds.stream()
                .map(participantDao::findPlayerById)
                .collect(Collectors.toList());

        return new ParticipantsResponse(ParticipantResponse.listOf(players));
    }

    public DealerResponse findDealer(Long gameId) {
        Long dealerId = blackjackgameDao.findDealerId(gameId);

        Dealer dealer = participantDao.findDealerById(dealerId);

        return DealerResponse.of(dealer);
    }

    public ParticipantResponse findPlayer(Long playerId) {
        Player player = participantDao.findPlayerById(playerId);

        return ParticipantResponse.of(player);
    }

    public AvailabilityResponse findPlayerAbleToTake(Long playerId) {
        Player player = participantDao.findPlayerById(playerId);

        return new AvailabilityResponse(player.isRunning());
    }

    @Transactional
    public void takePlayerCard(Long gameId, Long playerId, CardTakingRequest cardTakingRequest) {
        Long deckId = blackjackgameDao.findDeckId(gameId);
        Deck deck = deckDao.findDeckById(deckId);
        Player player = participantDao.findPlayerById(playerId);

        BlackjackGame blackjackGame = new BlackjackGame(player, deck);
        blackjackGame.takeTurnOf(cardTakingRequest.getIsTaking(), player);

        deckDao.update(deck);
        stateDao.updateByPlayer(player);
    }
}
