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
    protected int trainCardsDeckCount;
    protected int numDestinationCards;
    public static final String FACEUP_TRAIN_CARDS_UPDATE = "FaceUpTrainCardsUpdate";
    public static final String DECK_TRAIN_CARDS_UPDATE = "DeckTrainCardsUpdate";
    public static final String DESTINATION_CARDS_UPDATE = "DestinationCardsUpdate";

    public Deck() {
        this.revealed = new ArrayList<>();
    }

    public Deck(IDeck deck) {
        this.revealed = deck.getFaceUpTrainCards();
        this.trainCardsDeckCount = deck.getTrainCardsDeckCount();
        this.numDestinationCards = deck.getDestinationCardsCount();
    }

    public List<TrainCard> getFaceUpTrainCards() {
        return revealed;
    }
    
    @Override
    public void setFaceUpTrainCards(List<TrainCard> revealed) {
        this.revealed = revealed;
        this.setChanged();
        this.notifyObservers(FACEUP_TRAIN_CARDS_UPDATE);
    }
    
    public int getTrainCardsDeckCount() {
        return trainCardsDeckCount;
    }
    
    @Override
    public void setTrainCardsDeckCount(int count) {
		this.trainCardsDeckCount = count;
        this.setChanged();
        this.notifyObservers(DECK_TRAIN_CARDS_UPDATE);
    }
    
    public int getDestinationCardsCount() {
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
