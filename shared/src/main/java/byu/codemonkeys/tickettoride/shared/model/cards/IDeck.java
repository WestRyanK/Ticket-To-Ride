package byu.codemonkeys.tickettoride.shared.model.cards;

import java.util.List;
import java.util.Map;
import java.util.Set;

import byu.codemonkeys.tickettoride.shared.model.cards.DestinationCard;
import byu.codemonkeys.tickettoride.shared.model.cards.TrainCard;

public interface IDeck {
	TrainCard drawTrainCard();
	
	boolean drawFaceUpTrainCard(int index) ;
	
	Set<DestinationCard> drawDestinationCards();
	
	List<TrainCard> getFaceUpTrainCards();
	
	void setFaceUpTrainCards(List<TrainCard> revealed);
	
	int getTrainCardsDeckCount();
	
	void setTrainCardsDeckCount(int count);
	
	int getDestinationCardsCount();
	
	void setDestinationCardsCount(int count);

	void discard(Map<CardType, Integer> toDiscard);
}
