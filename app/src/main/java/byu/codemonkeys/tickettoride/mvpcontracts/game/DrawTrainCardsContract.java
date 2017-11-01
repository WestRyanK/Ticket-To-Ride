package byu.codemonkeys.tickettoride.mvpcontracts.game;

import java.util.List;

import byu.codemonkeys.tickettoride.shared.model.cards.TrainCard;

public interface DrawTrainCardsContract {
	interface View {
		void setFaceUpCards(List<TrainCard> cards);

		void setNumHidden(int count);
	}
	
	interface Presenter {
		void navigateBack();
		
		void drawDeckCard();
		
		void drawFaceUpCard(int cardIndex);

		void loadCards();
	}
}

