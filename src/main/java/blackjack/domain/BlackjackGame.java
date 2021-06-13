package blackjack.domain;

import blackjack.domain.card.Deck;
import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Participant;
import blackjack.domain.participant.Player;

import java.util.ArrayList;
import java.util.List;

public class BlackjackGame {
    private final Dealer dealer;
    private final List<Player> players;
    private final Deck deck;
    private final List<Participant> participants;

    public BlackjackGame(Dealer dealer, List<Player> players, Deck deck) {
        this.dealer = dealer;
        this.players = players;
        this.deck = deck;
        this.participants = new ArrayList<>();
        participants.add(dealer);
        participants.addAll(players);
    }

    public void initGame() {
        participants.forEach(participant -> participant.takeCards(deck.drawTwoCards()));
    }
}
