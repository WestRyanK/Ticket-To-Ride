package byu.codemonkeys.tickettoride.mvpcontracts.game;

import java.util.List;

import byu.codemonkeys.tickettoride.shared.model.cards.DestinationCard;


/**
 * Created by Ryan on 10/21/2017.
 */

public interface DrawDestinationCardsContract {
	interface View {
		List<DestinationCard> getSelectedCards();
		
		void setSelectedCards(List<DestinationCard> selectedCards);
		
		void setCards(List<DestinationCard> cards);
		
		void setMinCardsCount(int minCardsCount);
		
		void setCanContinue(boolean canContinue);
	}
	
	interface Presenter {
		void acceptSelectedCards();
		
		void loadDestinationCards();
		
		boolean canAccept();
		
		void selectionChanged();
	}
}
