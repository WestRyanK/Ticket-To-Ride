package byu.codemonkeys.tickettoride.mvpcontracts;

import java.util.List;

import byu.codemonkeys.tickettoride.models.TrainCard;

/**
 * Created by Ryan on 10/17/2017.
 */

public interface DrawTrainCardsContract {
	interface View {
		void setFaceUpCards(List<TrainCard> cards);
		
	}
	
	interface Presenter {
		void navigateBack();
		
		void drawDeckCard();
		
		void drawFaceUpCard(int cardIndex);
		
	}
}

