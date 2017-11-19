package byu.codemonkeys.tickettoride.shared.model.cards;

import java.util.List;
import java.util.Set;

import byu.codemonkeys.tickettoride.shared.model.cards.DestinationCard;
import byu.codemonkeys.tickettoride.shared.model.cards.TrainCard;

public interface IDeck {
	TrainCard drawTrainCard();
	
	Set<DestinationCard> drawDestinationCards();
	
	List<TrainCard> getFaceUp();
	
	void setFaceUp(List<TrainCard> revealed);
	
	int getNumHidden();
	
	void setNumHidden(int count);
	
	int getNumDestinationCards();
}
