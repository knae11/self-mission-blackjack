package blackjack.dto;

import blackjack.domain.card.Card;

import java.util.List;
import java.util.stream.Collectors;

public class CardResponse {
    private String suit;
    private String denomination;

    public CardResponse() {
    }

    public CardResponse(String suit, String denomination) {
        this.suit = suit;
        this.denomination = denomination;
    }

    private CardResponse(String cardId) {
        this.suit = String.valueOf(cardId.charAt(0));
        this.denomination = String.valueOf(cardId.charAt(1));
    }

    public static List<CardResponse> listOf(List<Card> cards) {
        return cards.stream()
                .map(card-> new CardResponse(card.getCardId()))
                .collect(Collectors.toList());
    }

    public String getSuit() {
        return suit;
    }

    public String getDenomination() {
        return denomination;
    }
}
