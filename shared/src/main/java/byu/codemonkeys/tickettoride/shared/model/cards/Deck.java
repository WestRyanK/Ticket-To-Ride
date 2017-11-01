package byu.codemonkeys.tickettoride.shared.model.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Deck implements IDeck {
    // The maximum number of revealed drawable cards
    private static int NUM_REVEALED = 5;

    protected List<TrainCard> revealed;
    protected int numHidden;
    protected int numDestinationCards;

    public Deck() {
        this.revealed = new ArrayList<>();
    }

    public Deck(IDeck deck) {
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

    @Override
    public TrainCard drawTrainCard() {
        return null;
    }

    @Override
    public Set<DestinationCard> drawDestinationCards() {
        return null;
    }
}
