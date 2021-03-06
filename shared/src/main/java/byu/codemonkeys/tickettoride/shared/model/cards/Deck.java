package byu.codemonkeys.tickettoride.shared.model.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
	
	/**
	 * Determine whether there are cards the player could choose
	 * that aren't face up wild cards. Eg if there are cards in the draw
	 * pile or other face up non wilds
	 * @return True if there are cards in the draw pile or other faceup nonwilds
	 */
	public boolean hasNonWildCardChoices(){
		boolean hasDeck = this.getTrainCardsDeckCount() > 0;
		boolean faceUpHasNonWild = false;
		for (TrainCard card : this.revealed){
			if (card != null && card.getCardColor() != CardType.Wild)
				faceUpHasNonWild = true;
		}
		return hasDeck || faceUpHasNonWild;
	}
	
	public int getDestinationCardsCount() {
		return numDestinationCards;
	}
	
	@Override
	public void setDestinationCardsCount(int count) {
		this.numDestinationCards = count;
		this.setChanged();
		this.notifyObservers(DESTINATION_CARDS_UPDATE);
	}

	@Override
	public void discard(Map<CardType, Integer> toDiscard) {
	}

	@Override
	public TrainCard drawTrainCard() {
		return null;
	}
	
	@Override
	public boolean drawFaceUpTrainCard(int index) {
		return false;
	}
	
	@Override
	public Set<DestinationCard> drawDestinationCards() {
		return null;
	}
}
