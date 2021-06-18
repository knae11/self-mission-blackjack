package blackjack.application;

import blackjack.dao.BlackjackGameDao;
import blackjack.dao.DealerDao;
import blackjack.dao.DeckCardDao;
import blackjack.dao.PlayerDao;
import blackjack.domain.BlackjackGame;
import blackjack.domain.card.Card;
import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Participant;
import blackjack.domain.participant.Player;
import blackjack.domain.result.ParticipantResult;
import blackjack.dto.*;
import blackjack.exception.web.ParticipantIdNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BlackjackService {

    private final BlackjackGameDao blackjackgameDao;
    private final PlayerDao playerDao;
    private final DealerDao dealerDao;
    private final DeckCardDao deckCardDao;

    public BlackjackService(BlackjackGameDao blackjackgameDao, PlayerDao playerDao, DealerDao dealerDao, DeckCardDao deckCardDao) {
        this.blackjackgameDao = blackjackgameDao;
        this.playerDao = playerDao;
        this.dealerDao = dealerDao;
        this.deckCardDao = deckCardDao;
    }

    @Transactional
    public BlackjackGameResponse createGame(List<PlayerRequest> playerRequests) {
        List<Player> players1 = convertToPlayers(playerRequests);
        BlackjackGame game = BlackjackGame.createInitial(players1);
        BlackjackGame createdGame = blackjackgameDao.create(game);

        return new BlackjackGameResponse(createdGame.getId(),
                DealerResponse.of(createdGame.getDealer()),
                ParticipantResponse.listOf(createdGame.getPlayers()));
    }

    private List<Player> convertToPlayers(List<PlayerRequest> playerRequests) {
        return playerRequests.stream()
                .map(request -> new Player(request.getName(), request.getBettingMoney()))
                .collect(Collectors.toList());
    }

    public BlackjackGameResponse findParticipants(Long gameId) {
        BlackjackGame blackjackGame = blackjackgameDao.findByGameId(gameId);

        return new BlackjackGameResponse(gameId,
                DealerResponse.of(blackjackGame.getDealer()),
                ParticipantResponse.listOf(blackjackGame.getPlayers()));
    }

    public ParticipantsResponse findPlayers(Long gameId) {
        List<Player> players = playerDao.findPlayersByGameId(gameId);

        return new ParticipantsResponse(ParticipantResponse.listOf(players));
    }

    public DealerResponse findDealer(Long gameId) {
        Dealer dealer = dealerDao.findByGameId(gameId);

        return DealerResponse.of(dealer);
    }

    public ParticipantResponse findPlayer(Long gameId, Long playerId) {
        Player player = playerDao.findByIds(gameId, playerId);

        return ParticipantResponse.of(player);
    }

    public AvailabilityResponse findPlayerAbleToTake(Long gameId, Long playerId) {
        Player player = playerDao.findByIds(gameId, playerId);

        return new AvailabilityResponse(player.isRunning());
    }

    @Transactional
    public void takePlayerCard(Long gameId, Long playerId, CardTakingRequest cardTakingRequest) {
        BlackjackGame game = blackjackgameDao.findForAPlayerTurn(gameId, playerId);
        Player player = game.getPlayers()
                .stream().filter(p -> p.getId().equals(playerId))
                .findFirst()
                .orElseThrow(ParticipantIdNotFoundException::new);

        List<Card> previousCards = player.getCards();
        game.takeTurnOf(cardTakingRequest.getIsTaking(), player);
        List<Card> currentCards = player.getCards();
        currentCards.removeAll(previousCards);

        deckCardDao.update(game.getDeck(), gameId);
        playerDao.update(player, currentCards);
    }

    public AvailabilityResponse findDealerAbleToTake(Long gameId) {
        Dealer dealer = dealerDao.findByGameId(gameId);

        return new AvailabilityResponse(dealer.isRunning());
    }

    @Transactional
    public void takeDealerCard(Long gameId, Long dealerId) {
        BlackjackGame game = blackjackgameDao.findForDealerTurn(gameId);

        Dealer dealer = game.getDealer();
        List<Card> previousCards = dealer.getCards();
        game.takeTurnOf(dealer);
        List<Card> currentCards = dealer.getCards();
        currentCards.removeAll(previousCards);

        deckCardDao.update(game.getDeck(), gameId);
        dealerDao.update(dealer, currentCards);
    }

    public List<ResultResponse> getResult(Long gameId) {
        BlackjackGame blackjackGame = blackjackgameDao.findByGameId(gameId);

        Map<Participant, ParticipantResult> result = blackjackGame.getResult();

        return ResultResponse.listOf(result);
    }
}
