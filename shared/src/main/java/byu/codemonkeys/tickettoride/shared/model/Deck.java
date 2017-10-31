package byu.codemonkeys.tickettoride.shared.model;

import java.util.List;

public class Deck {
    // The maximum number of revealed drawable cards
    private static int NUM_REVEALED = 5;

    protected List<TrainCard> revealed;
    protected int numHidden;
    protected int numDestinationCards;

    public Deck() {}

    public Deck(Deck deck) {
        this.revealed = deck.getRevealed();
        this.numHidden = deck.getNumHidden();
        this.numDestinationCards = deck.getNumDestinationCards();
    }

    public List<TrainCard> getRevealed() {
        return revealed;
    }

    public int getNumHidden() {
        return numHidden;
    }

    public int getNumDestinationCards() {
        return numDestinationCards;
    }
}
