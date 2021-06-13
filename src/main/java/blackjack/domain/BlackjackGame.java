package blackjack.domain;

import blackjack.domain.card.Deck;
import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Participant;
import blackjack.domain.participant.Player;
import blackjack.domain.state.State;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlackjackGame {
    private final Dealer dealer;
    private final List<Player> players;
    private final Deck deck;

    public BlackjackGame(Dealer dealer, List<Player> players, Deck deck) {
        this.dealer = dealer;
        this.players = players;
        this.deck = deck;
    }

    public BlackjackGame(Player player, Deck deck) {
        this(null, Collections.singletonList(player), deck);
    }

    public BlackjackGame(Player player) {
        this(null, Collections.singletonList(player), null);
    }

    public void initGame() {
        List<Participant> participants = new ArrayList<>();
        participants.add(dealer);
        participants.addAll(players);

        participants.forEach(participant -> participant.takeCards(deck.drawTwoCards()));
    }

    public boolean isAbleToTakeCardOf(Player player) {
        return player.isAbleToTake();
    }

    public State takeTurnOf(boolean acceptance, Player player) {
        return player.takeCard(acceptance, deck.drawCard());
    }
}
