package byu.codemonkeys.tickettoride.server.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class DeckTest {
    @Test
    public void constructorTest() {
        Deck deck = new Deck();

        assertEquals(36, deck.getNumDestinationCards());
    }
}