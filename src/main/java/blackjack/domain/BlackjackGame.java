package blackjack.domain;

import blackjack.domain.card.Deck;
import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Participant;
import blackjack.domain.participant.Player;
import blackjack.domain.result.ParticipantResult;
import blackjack.domain.result.ResultBoard;
import blackjack.domain.state.State;
import blackjack.exception.domain.GameNotEndException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BlackjackGame {
    private final Long id;
    private final Dealer dealer;
    private final List<Player> players;
    private final Deck deck;

    public BlackjackGame(Long id, Dealer dealer, List<Player> players, Deck deck) {
        this.id = id;
        this.dealer = dealer;
        this.players = players;
        this.deck = deck;
    }

    public BlackjackGame(Dealer dealer, List<Player> players, Deck deck) {
        this(null, dealer, players, deck);
    }

    public BlackjackGame(Player player, Deck deck) {
        this(null, Collections.singletonList(player), deck);
    }

    public BlackjackGame(Dealer dealer, Deck deck) {
        this(dealer, Collections.emptyList(), deck);
    }

    public BlackjackGame(Player player) {
        this(null, Collections.singletonList(player), null);
    }

    public BlackjackGame(Dealer dealer, List<Player> players) {
        this(dealer, players, null);
    }

    public static BlackjackGame createInitial(List<Player> players) {
        BlackjackGame blackjackGame = new BlackjackGame(new Dealer(), players, Deck.createShuffled());
        blackjackGame.initGame();

        return blackjackGame;
    }

    public static BlackjackGame create(long id, Dealer dealer, List<Player> players, Deck deck) {
        return new BlackjackGame(id, dealer, players, deck);
    }

    public static BlackjackGame create(long gameId, Dealer dealer, List<Player> players) {
        return new BlackjackGame(gameId, dealer, players, null);
    }

    public static BlackjackGame create(long gameId, Player player, Deck deck) {
        return new BlackjackGame(gameId, null, Collections.singletonList(player), deck);
    }

    public static BlackjackGame create(long id, Dealer dealer, Deck deck) {
        return new BlackjackGame(id, dealer, null, deck);
    }

    public void initGame() {
        List<Participant> participants = new ArrayList<>();
        participants.add(dealer);
        participants.addAll(players);

        participants.forEach(participant -> participant.takeCards(deck.drawTwoCards()));
    }

    public boolean isAbleToTakeCardOf(Player player) {
        return player.isRunning();
    }

    public boolean isAbleToTakeCardOf(Dealer dealer) {
        return dealer.isRunning();
    }

    public State takeTurnOf(boolean acceptance, Player player) {
        return player.takeCardForPlayer(acceptance, deck.drawCard());
    }

    public State takeTurnOf(Dealer dealer) {
        return dealer.takeCardForDealer(deck.drawCard());
    }

    public Map<Participant, ParticipantResult> getResult() {
        List<Participant> participants = new ArrayList<>();
        participants.add(dealer);
        participants.addAll(players);
        boolean allFinished = participants.stream().noneMatch(Participant::isRunning);

        if (allFinished) {
            ResultBoard resultBoard = new ResultBoard(dealer, players);
            return resultBoard.getResults();
        }
        throw new GameNotEndException();
    }

    public Dealer getDealer() {
        return dealer;
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public Deck getDeck() {
        return deck;
    }

    public Long getId() {
        return id;
    }


}
