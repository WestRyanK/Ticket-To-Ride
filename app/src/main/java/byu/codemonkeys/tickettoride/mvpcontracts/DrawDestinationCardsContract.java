package byu.codemonkeys.tickettoride.mvpcontracts;

import java.util.List;

import byu.codemonkeys.tickettoride.models.DestinationCard;

/**
 * Created by Ryan on 10/21/2017.
 */

public interface DrawDestinationCardsContract {
	interface View {
		List<DestinationCard> getSelectedCards();
		
		void setSelectedCards(List<DestinationCard> selectedCards);
		
		void setCards(List<DestinationCard> cards);
		
		void setCanContinue(boolean canContinue);
	}
	
	interface Presenter {
		void acceptSelectedCards();
	}
}
