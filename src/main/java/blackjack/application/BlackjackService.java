package blackjack.application;

import blackjack.dao.*;
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

    private final BlackjackGameRepository blackjackGameRepository;
    private final PlayerRepository playerRepository;
    private final DealerRepository dealerRepository;
    private final DeckCardRepository deckCardRepository;

    public BlackjackService(BlackjackGameRepository blackjackGameRepository, JdbcPlayerRepository playerRepository, JdbcDealerRepository dealerRepository, JdbcDeckCardRepository deckCardRepository) {
        this.blackjackGameRepository = blackjackGameRepository;
        this.playerRepository = playerRepository;
        this.dealerRepository = dealerRepository;
        this.deckCardRepository = deckCardRepository;
    }

    @Transactional
    public BlackjackGameResponse createGame(List<PlayerRequest> playerRequests) {
        List<Player> players1 = convertToPlayers(playerRequests);
        BlackjackGame game = BlackjackGame.createInitial(players1);
        BlackjackGame createdGame = blackjackGameRepository.create(game);

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
        BlackjackGame blackjackGame = blackjackGameRepository.findByGameId(gameId);

        return new BlackjackGameResponse(gameId,
                DealerResponse.of(blackjackGame.getDealer()),
                ParticipantResponse.listOf(blackjackGame.getPlayers()));
    }

    public ParticipantsResponse findPlayers(Long gameId) {
        List<Player> players = playerRepository.findPlayersByGameId(gameId);

        return new ParticipantsResponse(ParticipantResponse.listOf(players));
    }

    public DealerResponse findDealer(Long gameId) {
        Dealer dealer = dealerRepository.findByGameId(gameId);

        return DealerResponse.of(dealer);
    }

    public ParticipantResponse findPlayer(Long gameId, Long playerId) {
        Player player = playerRepository.findByIds(gameId, playerId);

        return ParticipantResponse.of(player);
    }

    public AvailabilityResponse findPlayerAbleToTake(Long gameId, Long playerId) {
        Player player = playerRepository.findByIds(gameId, playerId);

        return new AvailabilityResponse(player.isRunning());
    }

    @Transactional
    public void takePlayerCard(Long gameId, Long playerId, CardTakingRequest cardTakingRequest) {
        BlackjackGame game = blackjackGameRepository.findForAPlayerTurn(gameId, playerId);
        Player player = game.getPlayers()
                .stream().filter(p -> p.getId().equals(playerId))
                .findFirst()
                .orElseThrow(ParticipantIdNotFoundException::new);

        List<Card> previousCards = player.getCards();
        game.takeTurnOf(cardTakingRequest.getIsTaking(), player);
        List<Card> currentCards = player.getCards();
        currentCards.removeAll(previousCards);

        deckCardRepository.update(game.getDeck(), gameId);
        playerRepository.update(player, currentCards);
    }

    public AvailabilityResponse findDealerAbleToTake(Long gameId) {
        Dealer dealer = dealerRepository.findByGameId(gameId);

        return new AvailabilityResponse(dealer.isRunning());
    }

    @Transactional
    public void takeDealerCard(Long gameId, Long dealerId) {
        BlackjackGame game = blackjackGameRepository.findForDealerTurn(gameId);

        Dealer dealer = game.getDealer();
        List<Card> previousCards = dealer.getCards();
        game.takeTurnOf(dealer);
        List<Card> currentCards = dealer.getCards();
        currentCards.removeAll(previousCards);

        deckCardRepository.update(game.getDeck(), gameId);
        dealerRepository.update(dealer, currentCards);
    }

    public List<ResultResponse> getResult(Long gameId) {
        BlackjackGame blackjackGame = blackjackGameRepository.findByGameId(gameId);

        Map<Participant, ParticipantResult> result = blackjackGame.getResult();

        return ResultResponse.listOf(result);
    }
}
