package byu.codemonkeys.tickettoride.mvpcontracts.game;

import java.util.List;

import byu.codemonkeys.tickettoride.models.cards.DestinationCard;

/**
 * Created by Ryan on 10/18/2017.
 */

public interface DestinationCardsContract {
	interface View {
		
		void setDestinationCards(List<DestinationCard> cards);
	}
	
	interface Presenter {
		void navigateBack();
		
		void navigateDrawDestinationCards();
		
	}
}
