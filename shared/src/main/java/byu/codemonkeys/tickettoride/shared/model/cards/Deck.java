package byu.codemonkeys.tickettoride.shared.model.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import byu.codemonkeys.tickettoride.shared.model.ObservableExt;

public class Deck extends ObservableExt implements IDeck {
    // The maximum number of revealed drawable cards
    public static int NUM_REVEALED = 5;

    protected String type = "deck";

    protected List<TrainCard> revealed;
    protected int numHidden;
    protected int numDestinationCards;
    public static final String REVEALED_TRAIN_CARDS_UPDATE = "RevealedTrainCardsUpdate";
    public static final String DECK_TRAIN_CARDS_UPDATE = "DeckTrainCardsUpdate";
    public static final String DESTINATION_CARDS_UPDATE = "DestinationCardsUpdate";

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
    
    @Override
    public void setRevealed(List<TrainCard> revealed) {
        this.revealed = revealed;
        this.setChanged();
        this.notifyObservers(REVEALED_TRAIN_CARDS_UPDATE);
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
