package domain.card;

import exception.card.EmptyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("[도메인] 카드뭉치")
public class DeckTest {
    @DisplayName("덱 생성")
    @Test
    void createDeck() {
        assertThatCode(Deck::createBasic).doesNotThrowAnyException();
    }

    @DisplayName("기본 생성된 덱은 52장의 카드를 가짐")
    @Test
    void has52Cards() {
        Deck deck = Deck.createBasic();

        assertThat(deck.getCards()).hasSize(52);
    }

    @DisplayName("임의의 리스트로 덱 생성")
    @Test
    void createCustomDeck() {
        List<Card> cards = Arrays.asList(Card.of(Suit.CLOVER, Denomination.ACE),
                Card.of(Suit.HEART, Denomination.TEN),
                Card.of(Suit.HEART, Denomination.THREE));
        Deck deck = Deck.listOf(cards);

        assertThat(deck.getCards()).hasSize(3);
    }

    @DisplayName("셔플 덱 생성")
    @Test
    void createShuffledDeck() {
        Deck shuffledDeck = Deck.createShuffled();
        Deck basicDeck = Deck.createBasic();

        assertThat(shuffledDeck.getCards()).hasSize(52);
        assertThat(basicDeck.getCards()).hasSize(52);
        assertThat(shuffledDeck.getCards()).doesNotContainSequence(basicDeck.getCards());
    }

    @DisplayName("1장 카드 뽑기")
    @Test
    void drawCard() {
        List<Card> cards = Arrays.asList(Card.of(Suit.CLOVER, Denomination.ACE),
                Card.of(Suit.HEART, Denomination.TEN),
                Card.of(Suit.HEART, Denomination.THREE));
        Deck deck = Deck.listOf(cards);

        assertThat(deck.drawCard()).isEqualTo(Card.of(Suit.HEART, Denomination.THREE));
        assertThat(deck.getCards()).hasSize(2);
    }

    @DisplayName("2장 카드 뽑기")
    @Test
    void drawTwoCards() {
        List<Card> cards = Arrays.asList(Card.of(Suit.CLOVER, Denomination.ACE),
                Card.of(Suit.HEART, Denomination.TEN),
                Card.of(Suit.HEART, Denomination.THREE));
        Deck deck = Deck.listOf(cards);

        assertThat(deck.drawTwoCards()).contains(Card.of(Suit.HEART, Denomination.TEN), Card.of(Suit.HEART, Denomination.THREE));
        assertThat(deck.getCards()).hasSize(1);
    }

    @DisplayName("예외 - 카드 소진")
    @Test
    void emptyCardException() {
        List<Card> cards = Arrays.asList(Card.of(Suit.CLOVER, Denomination.ACE),
                Card.of(Suit.HEART, Denomination.TEN),
                Card.of(Suit.HEART, Denomination.THREE));
        Deck deck = Deck.listOf(cards);
        assertThat(deck.drawTwoCards());

        assertThatThrownBy(deck::drawTwoCards).isInstanceOf(EmptyException.class);
    }
}
