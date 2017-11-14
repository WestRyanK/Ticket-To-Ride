package byu.codemonkeys.tickettoride.mvpcontracts.game;

import java.util.List;

import byu.codemonkeys.tickettoride.shared.model.cards.DestinationCard;


/**
 * Created by Ryan on 10/18/2017.
 */

public interface DestinationCardsContract {
	interface View {
		
		void setDestinationCards(List<DestinationCard> cards);
		
		void setDestinationCardsInDeckCount(int cardsInDeckCount);
	}
	
	interface Presenter {
		void navigateBack();
		
		void navigateDrawDestinationCards();
		
		void loadDestinationCards();
	}
}
